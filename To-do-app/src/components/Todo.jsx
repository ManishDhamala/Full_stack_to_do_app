import React, { useReducer, useRef, useState } from 'react'
import todo_icon from '../assets/todo_icon.png'
import TodoItems from './TodoItems'
import { useEffect } from 'react';
import { deleteTask, getAllTasks, sendTask } from '../restful-api/DataFunction';

const Todo = () => {

// (Condition check) ? converting string into array to get the data from local storage and if empty returning null
  // const [todoList, setTodoList] = useState(localStorage.getItem("todos")? JSON.parse(localStorage.getItem("todos")) : []); 

  // const inputRef = useRef();


  // -----------------------------Practice backend------------------

  // Getting the data from the server

  const[tasks, setTasks] = useState([]);
  const [errorMessage, setErrorMessage]  = useState("");

  useEffect(()=>{
    const taskPromise = getAllTasks();
    taskPromise.then(response => {
      if(response.status == 200){
        console.log("Got the data");
        setTasks(response.data)
      }else{
        setErrorMessage("Error! - "+response.statusText)
      }
    }
  ).catch(response => {
    console.log("Something went wrong");
    setErrorMessage("Error! - "+ response);
  })

  },[]);

  // Sending the data to the server

  const initialState = {task:"", completed: false};

  const newTaskReducer = (state, data) => {
    return{...state, [data.field] : data.value };
  }

  const [newTask, dispatch] =useReducer(newTaskReducer,initialState);

  const handleOnChange = (e) => {
    dispatch({field:e.target.id, value:e.target.value});
  }

  console.log(newTask);

  const save = (e) => {
    e.preventDefault();
    const sendTaskPromise = sendTask(newTask);
    sendTaskPromise.then(response => {
      if(response.status === 200){
      console.log("New Task added")

       // Update the tasks state with the newly added task
       setTasks(prevTasks => [...prevTasks, newTask]);

      // Reset the newTask state to clear the input field
      dispatch({ field: 'task', value: '' });

      }else{
        setErrorMessage("Something went wrong"+response.statusText);
      }
    })
  }


  //Deleting the data from the server

  // const deleteTaskPromise = deleteTask(id);
  //     deleteTaskPromise.then(response => {
  //       if(response.status === 200 || response.status === 204){
  //         console.log("Task Deleted");
  //       }else{
  //         setErrorMessage("Error! - "+response.statusText);
  //       }
  //     })  .catch(error => {
  //           setErrorMessage("Failed to delete task! - " + error.message);
  //     });


  // Handling Delete Task with State Update
const handleDeleteTask = (id) => {
  deleteTask(id)
    .then(response => {
      if (response.status === 200 || response.status === 204) {
        console.log("Task Deleted");
        // Remove task from local state
        setTasks(prevTasks => prevTasks.filter(task => task.id !== id));
      } else {
        setErrorMessage(`Error! - ${response.statusText}`);
      }
    })
    .catch(error => {
      setErrorMessage(`Failed to delete task! - ${error.message}`);
    });
}


  // -----------------------------Practice backend------------------


  //Add
  // const add = () => {
  //   const inputText = inputRef.current.value.trim();

  //   if(inputText === ""){
  //     return null;
  //   }

  //   const newTodo = {
  //     id : Date.now(),
  //     text : inputText,
  //     isComplete : false
  //   }

  //   setTodoList((prev) => [...prev, newTodo]);
  //   inputRef.current.value = "";
  // }


  // Delete
  // const deleteTodo = (id)=> {
  //     setTodoList((prevTodos) => {
  //      return prevTodos.filter((todo) => todo.id !== id ) // Unmatched id(todo list) remains and vice-versa
  //     })
  // }

// Complete or not complete
  const toggle = (id) =>{
    setTodoList((prevTodos)=> {
        return prevTodos.map((todo)=> {
          if(todo.id === id){
            return {...todo, isComplete : !todo.isComplete}
          }
          return todo;
        })
    })
  }


  // useEffect(()=>{
  //   localStorage.setItem("todos", JSON.stringify(todoList)); // key, converting array into string
  // },[todoList])




  return (
    <div className='bg-white place-self-center w-11/12 max-w-md
    flex flex-col p-7 min-h-[550px] rounded-xl'>
      

        {/* -------------------title--------------------------- */}

        <div className='flex items-center mt-7 gap-2'>
            <img className='w-8' src={todo_icon} alt="todo_icon" />
            <h1 className='text-3xl font-semibold'>To-Do-List</h1>
            {errorMessage && <div className='error'> {errorMessage} </div>}
        </div>

        {/* -------------------input-box-------------------------- */}

      <div className='flex items-center my-7 bg-gray-200 rounded-full'>
        <input className='bg-transparent border-0 outline-none flex-1 h-14 
        pl-6 pr-2 placeholder:text-slate-600' 
        type="text" placeholder='Add your task'
         id="task" value={newTask.task} onChange={handleOnChange}/>
        <button onClick={save} className='border-none rounded-full bg-orange-600 w-32 h-14
         text-white text-lg font-medium cursor-pointer' > Add +</button>
      </div>

      {/* ------------------------todo list--------------------------- */}

      <div>
        {tasks.map((item, index) => {
          return <TodoItems
           key={index}
           text={item.task}
           id={item.id} 
           isComplete={item.completed}
           deleteTask={() => handleDeleteTask(item.id)} toggle={toggle}/>
        } )}

      </div>


    </div>
  )
}

export default Todo