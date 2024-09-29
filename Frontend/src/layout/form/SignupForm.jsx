import React from "react";


import { useFormik } from "formik";
import * as Yup from "yup";

import TextField from "@mui/material/TextField";

import "./loginPage.scss";
import { useNavigate } from "react-router-dom";
import Button from "@mui/material/Button";
import { useDispatch } from "react-redux";
import { register } from "../../../Storage/Auth/Actions";

const SignupForm = () => {

  const navigate = useNavigate();

const dispatch = useDispatch();

  const donorValidationSchema = Yup.object().shape({
    name: Yup.string().required("Name is Required"),
    email: Yup.string().email("Invalid email").required("Email is Required"),
    password: Yup.string().required("Password is Required"),
  });

  const handleFormSubmit=(values)=>{

    dispatch(register(values));

 }
  const formik = useFormik({
    initialValues: {
      name: "",
      email: "",
      password: "",
      role: "USER",
    },

    validationSchema: donorValidationSchema,
    onSubmit: handleFormSubmit,
  });



  return (
    <div className=" enterPage">
      <div className="formContainer flex justify-center  w-[70%] rounded-md mx-auto mt-16 md:mt-32 pt-4">
        <form
          className="w-[40%] space-y-8 ms-8 mb-4"
          onSubmit={formik.handleSubmit}
        >
          <TextField
            fullWidth
            variant="outlined"
            color="success"
            id="email"
            name="email"
            label="Email"
            value={formik.values.email}
            onChange={formik.handleChange}
            onBlur={formik.handleBlur}
            error={formik.touched.email && Boolean(formik.errors.email)}
            helperText={formik.touched.email && formik.errors.email}
          />
          <TextField
            fullWidth
            variant="outlined"
            color="success"
            id="name"
            name="name"
            label="Name"
            value={formik.values.name}
            onChange={formik.handleChange}
            onBlur={formik.handleBlur}
            error={formik.touched.name && Boolean(formik.errors.name)}
            helperText={formik.touched.name && formik.errors.name}
          />
          <TextField
            fullWidth
            variant="outlined"
            color="success"
            id="password"
            name="password"
            type="password"
            label="Password"
            value={formik.values.password}
            onChange={formik.handleChange}
            onBlur={formik.handleBlur}
            error={formik.touched.password && Boolean(formik.errors.password)}
            helperText={formik.touched.password && formik.errors.password}
            sx={{ borderRadius: "8px" }} // Add border radius here
          />

          <div className=" w-full items-end justify-center ">
            <Button
              sx={{
                width: "100%",
                height: "6vh",
                borderRadius: "20px",
                py: "10px",

                lg: "2",
                xs: "1",
                fontSize: 18,
              }}
              variant="contained"
              type="submit"
              id="signup-button"
            >
              Register
            </Button>
            <p className="text-center mt-2 text-black">
              Already have an account?{" "}
              <span
                onClick={() => navigate("/Authentication/signin")}
                className="text-blue-500 cursor-pointer"
              >
                {" "}
                Sign in
              </span>
            </p>
          </div>
        </form>
        <div className="w-full md:w-1/2 flex items-center justify-center -order-1 md:order-1">
          <div className="w-1/2 md:w-2/3">
            <img
              className="w-full"
              src="/src/assets/images/pages/signup.png"
              alt="Sign Up Pic"
            />
          </div>
        </div>
      </div>
    </div>
  );
};

export default SignupForm;
