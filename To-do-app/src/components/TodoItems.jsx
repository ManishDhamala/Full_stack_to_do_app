import React, { useState } from "react";
import tick from "../assets/tick.png";
import not_tick from "../assets/not_tick.png";
import delete_icon from "../assets/delete.png";
import edit_icon from "../assets/edit_icon.png";

const TodoItems = React.memo(
  ({ text, id, isComplete, deleteTask, toggle, editTask }) => {
    const [isEditing, setIsEditing] = useState(false); // State to track if we're editing
    const [editedText, setEditedText] = useState(text); // State to store new task text

    const handleEditClick = () => {
      setIsEditing(true); // Enable editing mode
    };

    const handleSaveClick = () => {
      editTask(id, editedText); // Call editTask function with the updated task text
      setIsEditing(false); // Exit editing mode
    };

    const handleCancelClick = () => {
      setEditedText(text); // Reset the text back to the original task text
      setIsEditing(false); // Exit editing mode
    };

    return (
      <div className="flex items-center my-3 gap-2">
        {/* Toggle status only when clicking on the tick icon */}
        <div className="flex items-center">
          <img
            onClick={() => toggle(id)} // This will only toggle completed status
            src={isComplete ? tick : not_tick}
            alt="tick-icon"
            className="w-7 cursor-pointer"
          />
        </div>

        <div className="flex flex-1 items-center">
          {isEditing ? (
            // Show input field in edit mode
            <input
              className="ml-4 text-[17px] border border-gray-400 p-1"
              value={editedText} //Original Task text
              onChange={(e) => setEditedText(e.target.value)} // Update editedText(stateful variable) state
            />
          ) : (
            // Show task text in view mode
            <p
              className={`text-slate-700 ml-4 text-[17px] decoration-slate-500 ${
                isComplete ? "line-through" : ""
              }`}
            >
              {text}
            </p>
          )}
        </div>

        {/* Show Save and Cancel buttons if editing, else show Edit button */}
        {isEditing ? (
          <>
            <button
              onClick={handleSaveClick}
              className=" w-4.5 mr-1 bg-green-600 text-white px-3 py-1 rounded"
            >
              Save
            </button>
            <button
              onClick={handleCancelClick}
              className=" bg-red-700 text-white px-3 py-1 rounded"
            >
              Cancel
            </button>
          </>
        ) : (
          <>
            <img
              onClick={handleEditClick}
              src={edit_icon}
              alt="edit_icon"
              className="w-4 cursor-pointer mr-3.5"
            />
            <img
              onClick={deleteTask}
              src={delete_icon}
              alt="delete_icon"
              className="w-3.5 cursor-pointer"
            />
          </>
        )}
      </div>
    );
  }
);

export default TodoItems;
