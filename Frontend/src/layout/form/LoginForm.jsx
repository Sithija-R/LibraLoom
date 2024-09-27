import { Link, useNavigate } from "react-router-dom";


import Button from "@mui/material/Button";

import * as Yup from "yup";

import "./loginPage.scss";
import { useFormik } from "formik";
import TextField from "@mui/material/TextField";
import { useDispatch } from "react-redux";
import { userLogin } from "../../../Storage/Auth/Actions";


const LoginForm = () => {
  const navigate = useNavigate();
  const dispatch = useDispatch();

  const validationSchema = Yup.object().shape({
    
    email: Yup.string().email("Invalid email").required("Email is Required"),
    password: Yup.string().required("Password is Required"),
  });

  const handleFormSubmit = (values) => {
    dispatch(userLogin(values));
  };
  const formik = useFormik({
    initialValues: {
      email: "",
      password: "" 
    },

    validationSchema: validationSchema,
    onSubmit: handleFormSubmit,
  });
  return (
    <div className="enterPage">
      <div className="formContainer w-80 rounded-md mx-auto mt-16 md:mt-32 pt-4  ">
        <form
          className="flex flex-col gap-5 px-5 py-10 w-[90%] mx-auto "
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
            id="password"
            name="password"
            type="password"
            label="Password"
            value={formik.values.password}
            onChange={formik.handleChange}
            onBlur={formik.handleBlur}
            error={formik.touched.password && Boolean(formik.errors.password)}
            helperText={formik.touched.password && formik.errors.password}
            sx={{ borderRadius: "8px" }} 
          />

          <div  className=" w-full items-end justify-center ">
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
            >
              Signin
            </Button>
          </div>

          <div className="flex flex-wrap justify-between h-20">
            <p className="text-black">Don,t have an account?</p>
            <Link className="text-blue-700 hover:underline" to="/signup">
            register?
            </Link>
          </div>
        </form>
      </div>
    </div>
  );
};

export default LoginForm;
