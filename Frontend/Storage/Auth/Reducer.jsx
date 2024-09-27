import { useDispatch } from "react-redux";
import { FIND_USER_BY_NAME_FAILURE, FIND_USER_BY_NAME_SUCCESS, FOLLOW_USER_SUCCESS, GET_USER_BY_ID_FAILURE, GET_USER_BY_ID_SUCCESS, GET_USER_FAILURE, GET_USER_REQUEST, GET_USER_SUCCESS, LOGIN_USER_FAILURE, LOGIN_USER_REQUEST, LOGIN_USER_SUCCESS, LOGOUT, REGISTER_USER_FAILURE, REGISTER_USER_REQUEST, REGISTER_USER_SUCCESS, UPDATE_USER_SUCCESS } from "./ActionTypes";


const initialState ={
    user:null,
    loading:null,
    error:null,
    jwt:null,
    anotherUser:null,
    findUser:null
}
export const authenticationReducer=(state=initialState,action)=>{


    
    switch (action.type) {
        case LOGIN_USER_REQUEST:
        case REGISTER_USER_REQUEST:
        case GET_USER_REQUEST:
            return{...state,loading:true,error:null}


        case LOGIN_USER_SUCCESS:
        case REGISTER_USER_SUCCESS:
            window.location.reload();
            return{...state,loading:false,error:null,jwt:action.payload}


        case GET_USER_SUCCESS:
            return{...state,loading:false,error:null,user:action.payload}

        case FIND_USER_BY_NAME_SUCCESS:
            return{...state,loading:false,error:null,findUser:action.payload}



        case FOLLOW_USER_SUCCESS:
            return{...state,loading:false,error:null,followUser:action.payload}

        case UPDATE_USER_SUCCESS:
            return{...state,loading:false,error:null,user:action.payload, updateUser:true}


        case GET_USER_BY_ID_SUCCESS:
            return{...state,loading:false,error:null,anotherUser:action.payload,userfetch:action.payload}

        case LOGIN_USER_FAILURE:
        case REGISTER_USER_FAILURE:
        case GET_USER_FAILURE:
        case GET_USER_BY_ID_FAILURE:
            return {...state,loading:false,error:action.payload}

        case FIND_USER_BY_NAME_FAILURE:
            return {...state,loading:false,error:action.payload,findUser:null}
        
        case LOGOUT:
            return initialState;

            
    
        default:
            return state;
    }
}  