# A Task Manager to manage your tasks

## Create a new task.
	POST	http://localhost:9000/tasks  
   	BODY 	{      
		 "name": "alerts_feature_addition_3",
		 "description": "adding a button to ui for alerts/incident tab",
		 "created_by": "Bhuvan",
		 "assignee": "Amar",
		 "status": "in-progress",
		 "severity": "l1"	
		 }

## Read all the available tasks
	GET 		http://localhost:9000/tasks
  
## Read task by id.
	GET 		http://localhost:9000/tasks/AOC_8
  
##  Update the existing task using id.
	POST 		http://localhost:9000/tasks/AOC_11/update

	BODY 	{      
		 "name": "alerts_feature_addition_3",
		 "description": "adding a button to ui for alerts/incident tab",
		 "created_by": "Bhuvan",
		 "assignee": "Amar",
		 "status": "in-progress",
		 "severity": "l1"	
		 }

## Delete the existing task.
	DELETE 		http://localhost:9000/tasks/AOC_1


