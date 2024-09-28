import React, { useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { logOut, updateUserProfile } from "../../Storage/Auth/Actions";
import LogoutIcon from "@mui/icons-material/Logout";
import { Box, Button, Modal, TextField, Typography } from "@mui/material";
import { useFormik } from "formik";


export const Navbar = () => {
  const dispatch = useDispatch();
  const { auth } = useSelector((store) => store);

  const [open, setOpen] = useState(false);
  const handleOpen = () => setOpen(true);
  const handleClose = () => setOpen(false);

  const style = {
    position: "absolute",
    top: "50%",
    left: "50%",
    transform: "translate(-50%, -50%)",
    width: 500,
    height: 250,
    bgcolor: "background.paper",
    borderRadius: "8px",
    boxShadow: 24,
    p: 4,
  };


 const handleprofileEdit=(values)=>{
  console.log("value ",values);
  handleClose();
  dispatch(updateUserProfile(values));
 }
  
  const formik = useFormik({
    initialValues: {
      name: auth.user?.name
    },
    onSubmit: handleprofileEdit,
  });



  const handleLogout = () => {
    dispatch(logOut());
  };

  return (
    <div className="mt-5 ml-5 rounded-xl p-5 h-[95vh] bg-gradient-to-br from-slate-600 to-black">
      <div className="h-[80vh]">
        <h2 className="text-white">Hi,{auth.user?.name}</h2>
        <div className="mt-5">

        <div
          onClick={handleOpen}
          className="flex items-center justify-stard cursor-pointer p-2 rounded-lg space-x-3 hover:bg-gray-600 "
        >
          <h2 className="text-white">Edit Profile</h2>
          
        </div>
        </div>
      </div>
      <div className="h-[14vh] flex items-center justify-center ">
        <div
          onClick={handleLogout}
          className="flex items-center justify-center cursor-pointer p-2 w-32 rounded-lg space-x-3 hover:bg-gray-700 "
        >
          <h2 className="text-white">Logout</h2>
          <LogoutIcon sx={{ color: "white" }} />
        </div>
      </div>

      <Modal
        open={open}
        onClose={handleClose}
        aria-labelledby="modal-modal-title"
        aria-describedby="modal-modal-description"
      >
        <Box sx={style}>
         <h1 className="font-semibold">Edit Profile</h1>
     
          <div className="mt-8 ">

          <form onSubmit={formik.handleSubmit}>
          <div>
          <TextField
            fullWidth
            variant="outlined"
           
            id="name"
            name="name"
            label="Name"
            value={formik.values.name}
            onChange={formik.handleChange}
            onBlur={formik.handleBlur}
            error={formik.touched.name && Boolean(formik.errors.name)}
            helperText={formik.touched.name && formik.errors.name}
          />
          </div>
          <div className="mt-3 flex justify-end space-x-2">

            <Button
              onClick={handleClose}
              sx={{
                width: "8%",
                height: "5vh",
                borderRadius: "10px",
                py: "10px",
                px: 5,
                lg: "2",
                xs: "1",
                fontSize: 13,
                backgroundColor: "rgba(255, 0, 0, 0.9)", 
               
                color: "white",
                "&:hover": {
                  backgroundColor: "rgba(255, 0, 0, 0.8)", 
                },
              }}
              variant="contained"
              
            >
              Cancel
            </Button>

            <Button
              
              sx={{
                width: "8%",
                height: "5vh",
                borderRadius: "10px",
                py: "10px",
                px: 5,
                lg: "2",
                xs: "1",
                fontSize: 13,
              }}
              variant="contained"
              type="submit"
            >
             Save
            </Button>
          </div>
          </form>

          </div>
        </Box>
      </Modal>
    </div>
  );
};
