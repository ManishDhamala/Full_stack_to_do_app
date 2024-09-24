import React from 'react'
import tick from '../assets/tick.png'
import not_tick from '../assets/not_tick.png'
import delete_icon from '../assets/delete.png'
import edit_icon from '../assets/edit_icon.png'



const TodoItems = ({text, id , isComplete, deleteTask, toggle}) => {
  return (
    <div className='flex items-center my-3 gap-2'>

        <div onClick={() => {toggle(id)}} className='flex flex-1 items-center cursor-pointer'>
            <img src= {isComplete ? tick : not_tick} alt="tick-icon" className='w-7'/>
            <p className={`text-slate-700 ml-4 text-[17px] decoration-slate-500
             ${isComplete ? "line-through" : ""}`}>
                {text}
            </p>
        </div>
        
        <img src={edit_icon} alt="delete_icon" className='w-4 cursor-pointer mr-3.5' />
        <img  onClick={deleteTask} src={delete_icon} alt="delete_icon" className='w-3.5 cursor-pointer' />

    </div>
  )
}

export default TodoItems;