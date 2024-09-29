import React, { useEffect, useState } from "react";
import SearchIcon from "@mui/icons-material/Search";
import { useNavigate } from "react-router-dom";
import {
  addBook,
  deleteBook,
  getAllBooks,
  searchBook,
} from "../../Storage/Book/Action";
import { useDispatch, useSelector } from "react-redux";
import { useFormik } from "formik";
import { Box, Button, Modal, TextField, Typography } from "@mui/material";
import * as Yup from "yup";
import Swal from "sweetalert2";
import EditBook from "./EditBook";
import ArrowBackIcon from '@mui/icons-material/ArrowBack';


const BookManage = () => {
  const dispatch = useDispatch();
  const navigate = useNavigate();

  const { auth, book } = useSelector((store) => store);

  const [keyword, setKeyWord] = useState("");
  const [open, setOpen] = useState(false);
  const [selectedBook, setSelectedBook] = useState(null);

  useEffect(() => {
    dispatch(getAllBooks());
    dispatch(searchBook(keyword));
  }, [book.borrowBook, book.returnBook, book.delete, book.addbook, keyword]);

  const handleSearch = () => {
    dispatch(searchBook(keyword));
  };

  const handleDelete = (isbn) => {
    Swal.fire({
      title: "Are you sure?",
      text: "You won't be able to revert this!",
      icon: "warning",
      showCancelButton: true,
      confirmButtonColor: "#3085d6",
      cancelButtonColor: "#d33",
      confirmButtonText: "Yes, delete it!",
    }).then((result) => {
      if (result.isConfirmed) {
        dispatch(deleteBook(isbn));
        Swal.fire({
          title: "Deleted!",
          text: "Book has been deleted.",
          icon: "success",
        });
      }
    });
  };

  const handleEdit = (item) => {
    setSelectedBook(item);
    setOpen(true)
    
  };

  const handleClose = () => {
    setOpen(false);
    setSelectedBook(null);
  };

  const validationSchema = Yup.object({
    title: Yup.string().required("Title is required"),
    author: Yup.string().required("Author is required"),
    isbn: Yup.string().required("ISBN is required"),
    publicationYear: Yup.number().required("Publication Year is required"),
  });

  const handleAddBook = (values) => {
    console.log(values);
    dispatch(addBook(values));
  };

  const formik = useFormik({
    initialValues: {
      title: "",
      author: "",
      isbn: "",
      publicationYear: "",
    },
    validationSchema: validationSchema,
    onSubmit: handleAddBook,
  });

  const style = {
    position: "absolute",
    top: "50%",
    left: "50%",
    transform: "translate(-50%, -50%)",
    width: 500,
    height: 400,
    bgcolor: "background.paper",
    borderRadius: "8px",
    boxShadow: 24,
    p: 4,
  };

  return (
    <div className="p-4">
      <div onClick={()=>navigate(-1)} className="flex w-20 cursor-pointer space-x-2 items-center">

      <ArrowBackIcon/>
      <h2 className="font-semibold text-lg">Back</h2>
      </div>
      <div className="flex justify-between mt-2">

      <h2 className="text-2xl font-bold mb-4 text-blue-700">Book Management</h2>
      <div className="mb-2 flex items-center justify-end ">
        <input
          type="text"
          className="w-80 rounded-3xl"
          onChange={(event) => {
            setKeyWord(event.target.value); 
            handleSearch();
          }}
        />
        <SearchIcon
          onClick={handleSearch}
          className="text-slate-400 cursor-pointer hover:text-blue-600"
          sx={{ fontSize: 50 }}
        />
      </div>
      </div>

      {/* Add New Book Section */}
      <div className="mb-4">
        <h3 className="text-lg -translate-y-3 font-semibold">Add New Book</h3>
        <form onSubmit={formik.handleSubmit}>
          <div className="mb-4">
            <TextField
              fullWidth
              variant="outlined"
              color="success"
              id="title"
              name="title"
              label="Book Name"
              value={formik.values.title}
              onChange={formik.handleChange}
              onBlur={formik.handleBlur}
              error={formik.touched.title && Boolean(formik.errors.title)}
              helperText={formik.touched.title && formik.errors.title}
            />
          </div>

          <div className="mb-4">
            <TextField
              fullWidth
              variant="outlined"
              color="success"
              id="author"
              name="author"
              label="Author"
              value={formik.values.author}
              onChange={formik.handleChange}
              onBlur={formik.handleBlur}
              error={formik.touched.author && Boolean(formik.errors.author)}
              helperText={formik.touched.author && formik.errors.author}
            />
          </div>

          <div className="mb-4">
            <TextField
              fullWidth
              variant="outlined"
              color="success"
              id="isbn"
              name="isbn"
              label="ISBN"
              value={formik.values.isbn}
              onChange={formik.handleChange}
              onBlur={formik.handleBlur}
              error={formik.touched.isbn && Boolean(formik.errors.isbn)}
              helperText={formik.touched.isbn && formik.errors.isbn}
            />
          </div>

          <div className="mb-4">
            <TextField
              fullWidth
              variant="outlined"
              color="success"
              id="publicationYear"
              name="publicationYear"
              label="Publication Year"
              value={formik.values.publicationYear}
              onChange={formik.handleChange}
              onBlur={formik.handleBlur}
              error={
                formik.touched.publicationYear &&
                Boolean(formik.errors.publicationYear)
              }
              helperText={
                formik.touched.publicationYear && formik.errors.publicationYear
              }
            />
          </div>

          <Button type="submit" variant="contained" color="primary">
            Add Book
          </Button>
        </form>
      </div>

      {/* Book List */}
      <div className="h-[300px] overflow-y-scroll border border-gray-300 rounded-md p-2">
        {keyword ? (
          <div>
            <h2 className="mt-2 font-semibold text-lg">Search Results..</h2>
            <div className="h-[40vh] overflow-y-scroll mt-4 space-y-2">
              {book.bookSearchResult && book.bookSearchResult.length > 0 ? (
                book.bookSearchResult.map((item) => (
                  <div
                    key={item.id}
                    className="flex justify-between items-center p-2 border-b"
                  >
                    <div>
                      <span className="font-semibold">{item.title}</span> (by{" "}
                      {item.author})<br />
                      <span>Publication Year: {item.publicationYear}</span>
                      <br />
                      <span>ISBN: {item.isbn}</span>
                    </div>
                    <div className="flex space-x-2">
                      <button
                        className="bg-yellow-500 text-white px-2 py-1 rounded-md"
                        onClick={() => handleEdit(item)} // Update to set selected book and open modal
                      >
                        Edit
                      </button>
                      <button
                        className="bg-red-500 text-white px-2 py-1 rounded-md"
                        onClick={() => handleDelete(item.isbn)}
                      >
                        Delete
                      </button>
                    </div>
                  </div>
                ))
              ) : (
                <span>No books available!</span>
              )}
            </div>
          </div>
        ) : (
          <div>
            <h2 className="mt-2 font-semibold text-lg">All Books</h2>
            <div className="mt-4 space-y-2">
              {book.books?.map((item) => (
                <div
                  key={item.id}
                  className="flex justify-between items-center p-2 border-b"
                >
                  <div>
                    <span className="font-semibold">{item.title}</span> (by{" "}
                    {item.author})<br />
                    <span>Publication Year: {item.publicationYear}</span>
                    <br />
                    <span>ISBN: {item.isbn}</span>
                  </div>
                  <div className="flex space-x-2">
                    <button
                      className="bg-yellow-500 text-white px-2 py-1 rounded-md"
                      onClick={() => handleEdit(item)} // Pass the item to handleEdit
                    >
                      Edit
                    </button>
                    <button
                      className="bg-red-500 text-white px-2 py-1 rounded-md"
                      onClick={() => handleDelete(item.isbn)}
                    >
                      Delete
                    </button>
                  </div>
                </div>
              ))}
            </div>
          </div>
        )}
      </div>

      {/* Edit Book Modal */}
      <Modal
        open={open}
        onClose={handleClose}
        aria-labelledby="modal-title"
        aria-describedby="modal-description"
      >
        <Box sx={style}>
          <h2 className="font-semibold mb-2">Edit Book</h2>
          <EditBook item={selectedBook} handleClose={handleClose} />
        </Box>
      </Modal>
    </div>
  );
};

export default BookManage;
