import {
    COMMENT_BOOK_FAILURE,
    COMMENT_BOOK_SUCCESS,
    CREATE_BOOK_FAILURE,
    CREATE_BOOK_REQUEST,
    CREATE_BOOK_SUCCESS,
    DELETE_BOOK_FAILURE,
    DELETE_BOOK_REQUEST,
    DELETE_BOOK_SUCCESS,
    FIND_BOOK_BY_CONTENT_FAILURE,
    FIND_BOOK_BY_CONTENT_REQUEST,
    FIND_BOOK_BY_CONTENT_SUCCESS,
    FIND_BOOK_BY_ID_FAILURE,
    FIND_BOOK_BY_ID_REQUEST,
    FIND_BOOK_BY_ID_SUCCESS,
    GET_ALL_BOOK_SUCCESS,
    GET_AVAILABLE_BOOK_SUCCESS,
    GET_LIKED_BOOK_FAILURE,
    GET_LIKED_BOOK_REQUEST,
    GET_LIKED_BOOK_SUCCESS,
    GET_USERS_ALL_BOOK_SUCCESS,
    LIKE_BOOK_FAILURE,
    LIKE_BOOK_REQUEST,
    LIKE_BOOK_SUCCESS,
    SUGGESTIONS_SUCCESS,

  } from "./ActionTypes";
  
  const initialState = {
    loading: false,
    data: null,
    error: null,
    books: [],
    availableBooks:[],
  
    searchBook: null,
    counter: null,
    bookSearchResult: null,
  };
  export const bookReducer = (state = initialState, action) => {
    switch (action.type) {
      case CREATE_BOOK_REQUEST:
      case DELETE_BOOK_REQUEST:
      case GET_LIKED_BOOK_REQUEST:
      case LIKE_BOOK_REQUEST:
      case FIND_BOOK_BY_CONTENT_REQUEST:
      case FIND_BOOK_BY_ID_REQUEST:
        return { ...state, loading: true, error: null };
  
      case CREATE_BOOK_FAILURE:
      case DELETE_BOOK_FAILURE:
      case GET_LIKED_BOOK_FAILURE:
      case LIKE_BOOK_FAILURE:
      case FIND_BOOK_BY_ID_FAILURE:
      case COMMENT_BOOK_FAILURE:
        return { ...state, loading: false, error: action.payload };
  
      case CREATE_BOOK_SUCCESS:
        return {
          ...state,
          loading: false,
          error: null,
          books: [action.payload, ...state.books],
        };
  
      case GET_ALL_BOOK_SUCCESS:
        return { ...state, loading: false, error: null, books: action.payload };

        case GET_AVAILABLE_BOOK_SUCCESS:
          return { ...state, loading: false, error: null, availableBooks: action.payload };
  
    
      case FIND_BOOK_BY_ID_SUCCESS:
        return {
          ...state,
          loading: false,
          error: null,
          searchBook: action.payload,
        };
  
      case DELETE_BOOK_SUCCESS:
        return {
          ...state,
          loading: false,
          error: null,
          books: state.books.filter((post) => post.id !== action.payload),
          delete: action.payload,
        };
  
      case FIND_BOOK_BY_CONTENT_SUCCESS:
        return {
          ...state,
          loading: false,
          error: null,
          bookSearchResult: action.payload,
        };
  
      case FIND_BOOK_BY_CONTENT_FAILURE:
        return {
          ...state,
          loading: false,
          error: action.payload,
          bookSearchResult: null,
        };
  
  
  
      default:
        return state;
    }
}