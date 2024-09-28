import { TextField, Button } from "@mui/material";
import { useFormik } from "formik";
import React from "react";
import { useDispatch } from "react-redux";
import { editBook } from "../../Storage/Book/Action";

const EditBook = ({ item, handleClose }) => {
  const dispatch = useDispatch();

  const handleEditBook = (values) => {
    handleClose()
    dispatch(editBook(values));
  };

  const formik = useFormik({
    initialValues: {
      title: item.title,
      author: item.author,
      isbn: item.isbn,
      publicationYear: item.publicationYear,
    },
    onSubmit: handleEditBook,
  });

  return (
    <div>
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

        <div>
          <Button type="submit" variant="contained" color="primary">
            Edit Book
          </Button>
          <Button onClick={handleClose} variant="outlined" color="secondary" style={{ marginLeft: '10px' }}>
            Cancel
          </Button>
        </div>
      </form>
    </div>
  );
};

export default EditBook;
