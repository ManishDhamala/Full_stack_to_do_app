import React, { useCallback, useReducer, useRef, useState } from "react";
import todo_icon from "../assets/todo_icon.png";
import TodoItems from "./TodoItems";
import { useEffect } from "react";
import {
  deleteTask,
  getAllTasks,
  sendTask,
  UpdateTask,
} from "../restful-api/DataFunction";

const Todo = () => {
  // -----------------------------Practice backend------------------

  // Getting the data from the server

  const [tasks, setTasks] = useState([]);
  const [errorMessage, setErrorMessage] = useState("");

  const inputRef = useRef(null);

  //Handling error
  const handleError = (error) => {
    setErrorMessage(`Error! - ${error.message || error.statusText}`); //error == response
  };

  useEffect(() => {
    const taskPromise = getAllTasks();
    taskPromise
      .then((response) => {
        if (response.status == 200) {
          console.log("Got the data");

          // Sorting tasks by their id values in asacending order
          const Tasks = response.data;

          setTasks(Tasks);
        } else {
          handleError(response); //Code reusability and optimization
        }
      })
      .catch(handleError); //Code reusability and optimization
  }, []);

  // Sending the data to the server
  const save = (e) => {
    e.preventDefault(); //Prevents form submission (NOT use)

    const taskText = inputRef.current.value.trim(); //Get value from the input field and remove front whitespace

    if (taskText.trim() === "") return; // Avoid submitting empty tasks

    const newTask = { task: taskText, completed: false };

    sendTask(newTask)
      .then((response) => {
        if (response.status === 200 || response.status === 201) {
          console.log("New Task added");
          const addedTask = response.data;

          // Update the tasks state with the newly added task
          setTasks((prevTasks) => [addedTask, ...prevTasks]);

          // Clear the input field
          inputRef.current.value = "";
        } else {
          handleError(response);
        }
      })
      .catch(handleError);
  };

  //Deleting the data from the server
  const handleDeleteTask = useCallback(
    (id) => {
      deleteTask(id) // Remove task from backend
        .then((response) => {
          if (response.status === 200 || response.status === 204) {
            console.log("Task Deleted");

            // Remove task from local state(Front-end)
            setTasks((prevTasks) => prevTasks.filter((task) => task.id !== id)); // Unmatched id(todo list) remains and vice-versa
          } else {
            handleError(response);
          }
        })
        .catch(handleError);
    },
    [tasks]
  );

  //Mark as complete or incomplete
  const toggle = useCallback(
    (id) => {
      //Store the task that matches the provided id
      const updatedTask = tasks.find((task) => task.id === id);

      if (updatedTask) {
        // Task remains same but the completed status change (If it was true (completed), it becomes false, and vice versa.)
        const updatedStatus = {
          ...updatedTask,
          completed: !updatedTask.completed,
        };

        // Update the task
        UpdateTask(updatedStatus)
          .then((response) => {
            if (response.status === 200) {
              console.log("Task status updated");

              // Update the tasks state in the frontend
              //If the provided id matches it return updated task else it return original task
              setTasks((prevTasks) =>
                prevTasks.map((task) => (task.id === id ? updatedStatus : task))
              );
            } else {
              handleError(response);
            }
          })
          .catch(handleError);
      }
    },
    [tasks]
  );

  //Edit the already added Tasks
  const editTask = useCallback(
    (id, newTaskText) => {
      const updatedTask = tasks.find((task) => task.id === id);

      if (updatedTask) {
        const updatedStatus = { ...updatedTask, task: newTaskText };

        UpdateTask(updatedStatus)
          .then((response) => {
            if (response.status === 200) {
              console.log("Task edited successfully");

              setTasks((prevTasks) =>
                prevTasks.map((task) => (task.id === id ? updatedStatus : task))
              );
            } else {
              handleError(response);
            }
          })
          .catch(handleError);
      }
    },
    [tasks]
  );

  // -----------------------------Practice backend------------------

  return (
    <div
      className="bg-white place-self-center w-11/12 max-w-md
    flex flex-col p-7 min-h-[550px] rounded-xl"
    >
      {/* -------------------title--------------------------- */}

      <div className="flex items-center mt-7 gap-2">
        <img className="w-8" src={todo_icon} alt="todo_icon" />
        <h1 className="text-3xl font-semibold">To-Do-List</h1>
        {errorMessage && <div className="error"> {errorMessage} </div>}
      </div>

      {/* -------------------input-box-------------------------- */}

      <div className="flex items-center my-7 bg-gray-200 rounded-full">
        <input
          className="bg-transparent border-0 outline-none flex-1 h-14 
        pl-6 pr-2 placeholder:text-slate-600"
          type="text"
          placeholder="Add your task"
          ref={inputRef}
          id="task"
          // value={newTask.task}
          // onChange={handleOnChange}
        />
        <button
          onClick={save}
          className="border-none rounded-full bg-orange-600 w-32 h-14
         text-white text-lg font-medium cursor-pointer"
        >
          {" "}
          Add +
        </button>
      </div>

      {/* ------------------------todo list--------------------------- */}

      <div>
        {tasks.map((item) => {
          // item = task & index = 0,1,2...
          return (
            <TodoItems
              key={item.id}
              text={item.task}
              id={item.id}
              isComplete={item.completed}
              deleteTask={() => handleDeleteTask(item.id)}
              toggle={toggle}
              editTask={editTask}
            />
          );
        })}
      </div>
    </div>
  );
};

export default Todo;
