import { api } from "../../src/ApiConfig/Apiconfig";
import { ADD_BOOK_FAILURE, ADD_BOOK_SUCCESS, BORROW_BOOK_FAILURE, BORROW_BOOK_SUCCESS, DELETE_BOOK_FAILURE, DELETE_BOOK_SUCCESS, EDIT_BOOK_FAILURE, EDIT_BOOK_SUCCESS, FIND_BOOK_BY_CONTENT_FAILURE, FIND_BOOK_BY_CONTENT_SUCCESS, GET_ALL_BOOK_FAILURE, GET_ALL_BOOK_SUCCESS, GET_AVAILABLE_BOOK_FAILURE, GET_AVAILABLE_BOOK_SUCCESS, RETURN_BOOK_FAILURE, RETURN_BOOK_SUCCESS } from "./ActionTypes";


export const getAllBooks=()=>async(dispatch)=>{
    console.log("dispatched")

    try {
        const {data} = await api.get("/api/book/get/all");
       
        console.log("data ",data)
        
        dispatch({type:GET_ALL_BOOK_SUCCESS,payload:data})
        
        
    } catch (error) {
        dispatch({type:GET_ALL_BOOK_FAILURE,payload:error.message})
    }

}

export const getAvailableBooks=()=>async(dispatch)=>{

    try {
        const {data} = await api.get("/api/book/get/available");
       
        
        dispatch({type:GET_AVAILABLE_BOOK_SUCCESS,payload:data})
        
        
    } catch (error) {
        dispatch({type:GET_AVAILABLE_BOOK_FAILURE,payload:error.message})
    }

}
export const getBookById=()=>async(dispatch)=>{

    try {
        const {data} = await api.get("/api/book/get/available");
       
        
        dispatch({type:GET_AVAILABLE_BOOK_SUCCESS,payload:data})
        
        
    } catch (error) {
        dispatch({type:GET_AVAILABLE_BOOK_FAILURE,payload:error.message})
    }

}


export const borrowBook=(transactionData)=>async(dispatch)=>{

    try {
        const {data} = await api.post(`/api/transaction/borrow`,transactionData);
       
        dispatch({type:BORROW_BOOK_SUCCESS,payload:data})
      
        Swal.fire({
            title: "Good job!",
            text: "Successfully Borrowed!",
            icon: "success"
          });
        
    } catch (error) {
        dispatch({type:BORROW_BOOK_FAILURE,payload:error.message})
    }

}

export const returnBook=(transactionData)=>async(dispatch)=>{

    try {
        const {data} = await api.post(`/api/transaction/return`,transactionData);
       
        dispatch({type:RETURN_BOOK_SUCCESS,payload:data})
      
        Swal.fire({
            title: "Good job!",
            text: "Successfully Borrowed!",
            icon: "success"
          });
        
    } catch (error) {
        dispatch({type:RETURN_BOOK_FAILURE,payload:error.message})
    }

}

export const searchBook = (keyword) => async (dispatch) => {
    try {
      
        const { data } = await api.get(`/api/book/search/${keyword}`);
        
        dispatch({
            type: FIND_BOOK_BY_CONTENT_SUCCESS,
            payload: data,
        });
    } catch (error) {
        dispatch({
            type: FIND_BOOK_BY_CONTENT_FAILURE,
            payload: error.message,
        });
    }
};


export const addBook=(bookData)=>async(dispatch)=>{

    try {
        const {data} = await api.post(`/api/book/add`,bookData);
       
        dispatch({type:ADD_BOOK_SUCCESS,payload:data})
      
        Swal.fire({
            title: "Good job!",
            text: "Successfully Added!",
            icon: "success"
          });
        
    } catch (error) {
        dispatch({type:ADD_BOOK_FAILURE,payload:error.message})
    }

}

export const editBook=(bookData)=>async(dispatch)=>{

    try {
        const {data} = await api.post(`/api/book/edit`,bookData);
       
        dispatch({type:EDIT_BOOK_SUCCESS,payload:data})
      
        Swal.fire({
            title: "Good job!",
            text: "Successfully Edited!",
            icon: "success"
          });
        
    } catch (error) {
        dispatch({type:EDIT_BOOK_FAILURE,payload:error.message})
    }
    
}
export const deleteBook=(isbn)=>async(dispatch)=>{

    try {
        const {data} = await api.delete(`/api/book/delete/${isbn}`);
       
        
        dispatch({type:DELETE_BOOK_SUCCESS,payload:data})
        
        
    } catch (error) {
        dispatch({type:DELETE_BOOK_FAILURE,payload:error.message})
    }

}