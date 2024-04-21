# Function to find the maximum of two values
def max1(a, b):
    if a > b:
        return a
    else:
        return b

# Function to display the timestamps of events in both processes
def display(e1, e2, p1, p2):
    print("\nThe time stamps of events in P1:")
    print(" ".join(map(str, p1)))
    print("\nThe time stamps of events in P2:")
    print(" ".join(map(str, p2)))

# Function to implement Lamport logical clock algorithm
def lamport_logical_clock(e1, e2, m):
    # Initialize lists to store timestamps for events in P1 and P2
    p1 = list(range(1, e1 + 1))
    p2 = list(range(1, e2 + 1))

    # Print the headers for events in P2
    print("\te2", end="")
    for i in range(e2):
        print("\te2" + str(i + 1), end="")
    print()

    # Print the communication matrix
    for i in range(e1):
        print("e1" + str(i + 1) + "\t", end="")
        for j in range(e2):
            print(str(m[i][j]) + "\t", end="")
        print()

    # Update timestamps based on communication events
    for i in range(e1):
        for j in range(e2):
            # If P1's event sends a message to P2's event
            if m[i][j] == 1:
                # Update P2's timestamp for the receiving event
                p2[j] = max1(p2[j], p1[i] + 1)
                # Increment timestamps for subsequent events in P2 to maintain causality
                for k in range(j + 1, e2):
                    p2[k] = p2[k - 1] + 1
            # If P2's event sends a message to P1's event
            if m[i][j] == -1:
                # Update P1's timestamp for the receiving event
                p1[i] = max1(p1[i], p2[j] + 1)
                # Increment timestamps for subsequent events in P1 to maintain causality
                for k in range(i + 1, e1):
                    p1[k] = p1[k - 1] + 1

    # Display the timestamps of events in both processes
    display(e1, e2, p1, p2)

# Main section
if __name__ == "__main__":
    # Define the number of events in P1 and P2
    e1 = 5
    e2 = 3

    # Define the communication matrix m
    m = [[0, 0, 0],
         [0, 0, 1],
         [0, 0, 0],
         [0, 0, 0],
         [0, -1, 0]]

    # Call the lamport_logical_clock function with the defined parameters
    lamport_logical_clock(e1, e2, m)
