import{applyMiddleware, combineReducers, legacy_createStore} from "redux";
import { thunk } from 'redux-thunk';
import { authenticationReducer } from "./Auth/Reducer";
import { bookReducer } from "./Book/Reducer";




const rootReducers = combineReducers({

    auth:authenticationReducer,
    book:bookReducer
    
});

export const store =legacy_createStore(rootReducers,applyMiddleware(thunk));