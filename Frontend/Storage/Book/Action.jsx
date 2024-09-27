import { api } from "../../src/ApiConfig/Apiconfig";
import { GET_ALL_BOOK_FAILURE, GET_ALL_BOOK_SUCCESS, GET_AVAILABLE_BOOK_FAILURE, GET_AVAILABLE_BOOK_SUCCESS } from "./ActionTypes";


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