import { api } from "../../src/ApiConfig/Apiconfig";
import { BORROW_BOOK_FAILURE, BORROW_BOOK_SUCCESS, GET_ALL_BOOK_FAILURE, GET_ALL_BOOK_SUCCESS, GET_AVAILABLE_BOOK_FAILURE, GET_AVAILABLE_BOOK_SUCCESS, RETURN_BOOK_FAILURE, RETURN_BOOK_SUCCESS } from "./ActionTypes";


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