import {
  ADD_BOOK_FAILURE,
  ADD_BOOK_REQUEST,
  ADD_BOOK_SUCCESS,
  BORROW_BOOK_SUCCESS,
  COMMENT_BOOK_FAILURE,
  DELETE_BOOK_FAILURE,
  DELETE_BOOK_REQUEST,
  DELETE_BOOK_SUCCESS,
  EDIT_BOOK_FAILURE,
  EDIT_BOOK_REQUEST,
  EDIT_BOOK_SUCCESS,
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
  LIKE_BOOK_FAILURE,
  LIKE_BOOK_REQUEST,
  RETURN_BOOK_SUCCESS,
} from "./ActionTypes";

const initialState = {
  loading: false,
  data: null,
  error: null,
  books: [],
  availableBooks: [],

  searchBook: null,
  counter: null,
  bookSearchResult: null,
};
export const bookReducer = (state = initialState, action) => {
  switch (action.type) {
    case ADD_BOOK_REQUEST:
    case EDIT_BOOK_REQUEST:
    case DELETE_BOOK_REQUEST:
    case GET_LIKED_BOOK_REQUEST:
    case LIKE_BOOK_REQUEST:
    case FIND_BOOK_BY_CONTENT_REQUEST:
    case FIND_BOOK_BY_ID_REQUEST:
      return { ...state, loading: true, error: null };

    case ADD_BOOK_FAILURE:
    case EDIT_BOOK_FAILURE:
    case DELETE_BOOK_FAILURE:
    case GET_LIKED_BOOK_FAILURE:
    case LIKE_BOOK_FAILURE:
    case FIND_BOOK_BY_ID_FAILURE:
    case COMMENT_BOOK_FAILURE:
      return { ...state, loading: false, error: action.payload };

    case ADD_BOOK_SUCCESS:
    case EDIT_BOOK_SUCCESS:
      return { ...state, loading: false, error: null, addbook: action.payload };

    case BORROW_BOOK_SUCCESS:
      return {
        ...state,
        loading: false,
        error: null,
        borrowBook: action.payload,
      };

    case RETURN_BOOK_SUCCESS:
      return {
        ...state,
        loading: false,
        error: null,
        returnBook: action.payload,
      };

    case GET_ALL_BOOK_SUCCESS:
      return { ...state, loading: false, error: null, books: action.payload };

    case GET_AVAILABLE_BOOK_SUCCESS:
      return {
        ...state,
        loading: false,
        error: null,
        availableBooks: action.payload,
      };

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
};
