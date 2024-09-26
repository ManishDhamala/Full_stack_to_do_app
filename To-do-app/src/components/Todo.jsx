import React, { useReducer, useRef, useState } from "react";
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

  useEffect(() => {
    const taskPromise = getAllTasks();
    taskPromise
      .then((response) => {
        if (response.status == 200) {
          console.log("Got the data");

          // Sorting tasks by id before setting state
          const sortedTasks = response.data.sort((a, b) => a.id - b.id);

          setTasks(sortedTasks);
        } else {
          setErrorMessage("Error! - " + response.statusText);
        }
      })
      .catch((response) => {
        console.log("Something went wrong");
        setErrorMessage("Error! - " + response);
      });
  }, []);

  // Sending the data to the server

  const initialState = { task: "", completed: false };

  const newTaskReducer = (state, data) => {
    return { ...state, [data.field]: data.value };
  };

  const [newTask, dispatch] = useReducer(newTaskReducer, initialState);

  const handleOnChange = (e) => {
    dispatch({ field: e.target.id, value: e.target.value });
  };

  console.log(newTask);

  const save = (e) => {
    e.preventDefault();
    const sendTaskPromise = sendTask(newTask);
    sendTaskPromise.then((response) => {
      if (response.status === 200) {
        console.log("New Task added");

        // Assuming the response contains the task with its assigned ID from the backend
        const addedTask = response.data;

        // Update the tasks state with the newly added task
        setTasks((prevTasks) => [...prevTasks, addedTask]);

        // Reset the newTask state to clear the input field
        dispatch({ field: "task", value: "" });
      } else {
        setErrorMessage("Something went wrong" + response.statusText);
      }
    });
  };

  //Deleting the data from the server
  const handleDeleteTask = (id) => {
    deleteTask(id)
      .then((response) => {
        if (response.status === 200 || response.status === 204) {
          console.log("Task Deleted");
          // Remove task from local state
          setTasks((prevTasks) => prevTasks.filter((task) => task.id !== id)); // Unmatched id(todo list) remains and vice-versa
        } else {
          setErrorMessage(`Error! - ${response.statusText}`);
        }
      })
      .catch((error) => {
        setErrorMessage(`Failed to delete task! - ${error.message}`);
      });
  };

  //Mark as complete or incomplete
  const toggle = (id) => {
    //Store the task that matches the provided id
    const updatedTask = tasks.find((task) => task.id === id);

    if (updatedTask) {
      // Task remains same but the completed status change (If it was true (completed), it becomes false, and vice versa.[Backend])
      const updatedStatus = {
        ...updatedTask,
        completed: !updatedTask.completed,
      };

      // Update the task based on id
      UpdateTask(id, updatedStatus)
        .then((response) => {
          if (response.status === 200) {
            console.log("Task status updated");

            // Update the tasks state in the frontend
            //If the provided id matches it return updated task else it return original task[Frontend]
            setTasks((prevTasks) =>
              prevTasks.map((task) => (task.id === id ? updatedStatus : task))
            );
          } else {
            setErrorMessage(
              "Error updating task status: " + response.statusText
            );
          }
        })
        .catch((error) => {
          setErrorMessage("Failed to update task status! - " + error.message);
        });
    }
  };

  //Edit the already added Tasks

  const editTask = (id, newTaskText) => {
    const updatedTask = tasks.find((task) => task.id === id);

    if (updatedTask) {
      const updatedStatus = { ...updatedTask, task: newTaskText };

      UpdateTask(id, updatedStatus)
        .then((response) => {
          if (response.status === 200) {
            console.log("Task edited successfully");

            setTasks((prevTasks) =>
              prevTasks.map((task) => (task.id === id ? updatedStatus : task))
            );
          } else {
            setErrorMessage(
              "Error editing task status: " + response.statusText
            );
          }
        })
        .catch((error) => {
          setErrorMessage("Failed to edit task status! - " + error.message);
        });
    }
  };

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
          id="task"
          value={newTask.task}
          onChange={handleOnChange}
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
        {tasks.map((item, index) => {
          return (
            <TodoItems
              key={index}
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
