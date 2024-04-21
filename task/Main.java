import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Server {
private final String name;
private final Map<String, Integer> executionCosts; // Execution costs for each task
private int load;

public Server(String name) {
this.name = name;
this.executionCosts = new HashMap<>();
this.load = 0;
}

public String getName() {
return name;
}

public int getLoad() {
return load;
}

public void increaseLoad() {
load++;
}

public void decreaseLoad() {
load--;
}

public void setExecutionCost(String task, int cost) {
executionCosts.put(task, cost);
}

public int getExecutionCost(String task) {
return executionCosts.getOrDefault(task, 0); // Default to 0 if task not found
}
}

class LoadBalancer {
private final List<Server> servers;
private int currentIndex;

public LoadBalancer(List<Server> servers) {
this.servers = servers;

this.currentIndex = 0;
}

public Server getNextServer() {
Server nextServer = servers.get(currentIndex);
currentIndex = (currentIndex + 1) % servers.size();
return nextServer;
}
}

class Main {
private static final int COMMUNICATION_COST_PER_REQUEST = 2;

public static void main(String[] args) {
// Create servers
List<Server> servers = new ArrayList<>();
Server server1 = new Server("Server 1");
Server server2 = new Server("Server 2");
Server server3 = new Server("Server 3");

// Define execution costs for tasks on each server
server1.setExecutionCost("Task A", 5);
server1.setExecutionCost("Task B", 7);
server2.setExecutionCost("Task A", 6);
server2.setExecutionCost("Task B", 8);

server3.setExecutionCost("Task A", 4);
server3.setExecutionCost("Task B", 6);

servers.add(server1);
servers.add(server2);
servers.add(server3);

LoadBalancer loadBalancer = new LoadBalancer(servers);

int totalExecutionCost = 0;
int totalCommunicationCost = 0;

// Simulate requests
String[] tasks = {"Task A", "Task B"};
for (int i = 0; i < 10; i++) {
String task = tasks[i % tasks.length]; // Rotate through tasks
Server server = loadBalancer.getNextServer();
server.increaseLoad();

int executionCost = server.getExecutionCost(task);
totalExecutionCost += executionCost;

totalCommunicationCost += COMMUNICATION_COST_PER_REQUEST;
System.out.println("Request " + i + " (Task: " + task + ") handled by " + server.getName()
+

" (Execution Cost: $" + executionCost + ")");
}

// Print server loads
for (Server server : servers) {
System.out.println(server.getName() + " load: " + server.getLoad());
}

// Calculate total cost
int totalCost = totalExecutionCost + totalCommunicationCost;
System.out.println("Total Execution Cost: $" + totalExecutionCost);
System.out.println("Total Communication Cost: $" + totalCommunicationCost);
System.out.println("Total Cost: $" + totalCost);
}
}