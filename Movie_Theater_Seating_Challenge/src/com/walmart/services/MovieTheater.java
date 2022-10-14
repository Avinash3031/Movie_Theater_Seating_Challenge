package com.walmart.services;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Stack;

import com.walmart.models.Request;
import com.walmart.models.Response;
import com.walmart.models.SafetyRiskFactor;
import com.walmart.models.Seat;

public class MovieTheater {
	
	private Seat[][] seats;
	private SafetyRiskFactor riskFactor;
	private String address;
	private int buffer;
	private Stack<PriorityQueue<Seat>> availableSeatsInRow = new Stack<PriorityQueue<Seat>>();
	private int totalAvailableSeats = 0;

	public MovieTheater(SafetyRiskFactor riskFactor, int buffer) {
		super();
		this.riskFactor = riskFactor;
		this.buffer = buffer;
	}

	public MovieTheater(int rows, int columns, SafetyRiskFactor riskFactor, int buffer) {
		super();
		this.buffer = buffer;
		// char rowSymbol = (char) ('A' + rows-1);
		char rowSymbol =  'A';
		this.seats = new Seat[rows][columns];
		this.riskFactor = riskFactor;
		
		for(int j = 0; j < rows; j++) {
			// each have a priority Queue
			PriorityQueue<Seat> pq = new PriorityQueue<>();
			for(int i = 0; i < columns; i++){
				this.seats[j][i] = new Seat();
				// seating seatId
				this.seats[j][i].setSeatId(rowSymbol + String.valueOf(i+1));
				// assigning priority
	            this.seats[j][i].setPriority(columns - i);
	            // adding to queue
	            pq.add(this.seats[j][i]);
	            this.seats[j][i].setIsBlocked(false);
	            
	        }
			
			if(this.riskFactor.equals(SafetyRiskFactor.HIGH)) {
				if(rows%2 == 0 && j%2 != 0 || rows%2 != 0 && j%2 == 0) {
					this.availableSeatsInRow.push(pq);
					this.totalAvailableSeats+=pq.size();
				}
			}else {
				this.availableSeatsInRow.push(pq);
				this.totalAvailableSeats+=pq.size();
			}
			
			rowSymbol++;
		}

	}


	public Seat[][] getSeats() {
		return seats;
	}


	public void setSeats(Seat[][] seats) {
		this.seats = seats;
	}


	public SafetyRiskFactor getRiskFactor() {
		return riskFactor;
	}


	public void setRiskFactor(SafetyRiskFactor riskFactor) {
		this.riskFactor = riskFactor;
	}


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}


	public Response bookSeat(Request request) {
		// response object
		Response response = new Response(request.getRequestid(), null, null);
		
		// if no available seats return
		if(totalAvailableSeats < request.getNumberOfSeats()) {
			response.setStatus("FULL");
			return response;
		}
		int i = request.getNumberOfSeats();
		
		List<String> seatIds = new ArrayList<>();
//		O(1)
		PriorityQueue<Seat> topRow = availableSeatsInRow.pop();
//		O(k)
		while(i != 0) {
			if(topRow.size() == 0 && availableSeatsInRow.size() != 0) {
//				O(1)
				topRow = availableSeatsInRow.pop();
			}
//			O(log n)
			seatIds.add(topRow.poll().getSeatId());
			i--;
			this.totalAvailableSeats--;
		}
		
		response.setSeatId(seatIds);
		response.setStatus("SUCCESS");
		
		if(request.getNumberOfSeats() == 0) {
			availableSeatsInRow.push(topRow);
			return response;
		}
		
		// blocking to right
		int blocking3 = this.buffer;
		while(topRow.size() > 0 && blocking3 != 0 && !this.riskFactor.equals(SafetyRiskFactor.LOW)) {
			topRow.poll();
			blocking3--;
		}
		if(topRow.size() > 0) {
			availableSeatsInRow.push(topRow);
		}
		
		return response;
	}
	
	
	
	
	

}
