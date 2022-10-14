### Movie Theater Seating Challenge

##### Program Inputs:
- Takes an input file from command line argument
- File contains a list of:
	- RequestId numberOfSeats
- Assuming input doesn't change.

##### Program Output:
- Outputs a file name which contains following:
	- RequestId [seatsNumbers(comma separated)]

#### Problem Statement Overview:

Implement an algorithm for assigning seats within a movie theater to
fulfill reservation requests. Assume the movie theater has the seating
arrangement of 10 rows x 20 seats, as illustrated to the right.

Your homework assignment is to design and write a seat assignment
program to **maximize both customer satisfaction and customer
safety.** For the purpose of public safety, assume that a buffer of three
seats and/or one row is required.

##### Assumptions:

These assumptions are made to handle the customer satisfaction.
- Assuming that the group can be either be a family or friends who wish to enjoy the movie by seating together.
- The system will not reserve seats for a group if the requested number of seats is greater than the available seats. In that case, the customers are informed about the insufficient number of available seats.
- Max number of row a theater could have is 26.

#### Goals:
##### Customer Satisfaction:

- We follow the "first come first serve" principle to satisfy the customer, which means seats are filled starting from the top row as people love to watch movies from the top row. 
- Allowing group of people seating together with their loved ones and family. As we can clear see from the problem statement and examples that people are reserving seats in group. 
So the priority will be to allocate seats for the group in a single row. 
if the number of people exceeds the row capacity, try allocating the remaining group in another row. 

##### Customer Safety:

Considering following properties and deciding the factor or level of safeness.
- High: Should have a buffer of 3 seats in between two groups and alternative rows should be filled.
- Medium: Only maintain a buffer of 3 seats in between two groups.
- Low: No safety precaution is needed. [Not implementing this, as this not part of requirements]

#### Steps to execute the program

- Open eclipse
- Import project as Java project.
- Make changes to "Run configuration" :- add the path of input file as command line arguments.
- Run the MovieTheaterApp.java


