import axios from "axios";


// Getting all Tasks from the server
export const getAllTasks = () => {
    const taskPromise = axios({
        url: "http://localhost:8080/api/v1/tasks/",
        method: "GET",
        headers: new Headers({ 'Accept': 'application/json' })
    });

    return taskPromise;
}

// Sending Task to the server
export const sendTask = (task) => {
    return axios({
        url: "http://localhost:8080/api/v1/tasks/",
        method: "POST",
        headers: new Headers({ 'Accept': 'application/json', 'ContentType': 'application/json' }),
        data: task
    });
}

// Deleting Task from the server
export const deleteTask = (id) => {
    return axios.delete(`http://localhost:8080/api/v1/tasks/${id}`, {
        headers: { 'Accept': 'application/json' }
    });
}