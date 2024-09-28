import React, { useState } from 'react'
import Button from "@mui/material/Button";
import { Box, Modal, Typography } from '@mui/material';
import { useDispatch, useSelector } from 'react-redux';
import { borrowBook } from '../../../../Storage/Book/Action';
import Swal from 'sweetalert2';
export const BookCard = ({item}) => {
 
  const dispatch = useDispatch();

  const { auth } = useSelector((store) => store);

  const [open, setOpen] = useState(false);
  const handleOpen = () => setOpen(true);
  const handleClose = () => setOpen(false);


  const style = {
    position: 'absolute' ,
    top: '50%',
    left: '50%',
    transform: 'translate(-50%, -50%)',
    width: 600,
    height:290,
    bgcolor: 'background.paper',
   borderRadius:"8px",
    boxShadow: 24,
    p: 4,
  };

  

const handleBorrow=()=>{

  if(auth.user?.incompleteTransaction){
    handleClose();
    Swal.fire({
      title: "Warning !",
      text: "You need to return borrowed book before borrow another.",
      icon: "error"
    });
  }

  else {
    const transactionData={ userId:auth.user?.userId,isbn:item?.isbn}
    // Dispatch the borrowBook action if no incomplete transaction exists
    dispatch(borrowBook(transactionData));
    handleClose(); // Close the modal or popup (if that’s what `handleClose` does)
  }

  
}

  return (
    <div className='w-full h-32 px-3 py-1 flex justify-between  bg-white dark:bg-slate-800 shadow-lg rounded-lg border border-slate-200 dark:border-slate-700'>
        <div>

        <h2 className='text-lg font-semibold'>{item?.title}</h2>
        <h2><span className='font-semibold text-blue-800'>ISBN :</span> {item?.isbn}</h2>
        <h3><span className='font-semibold text-blue-800'>Author :</span> {item?.author}</h3>
        <h3><span className='font-semibold text-blue-800'>Publication Year :</span> {item?.publicationYear}</h3>
        </div>
        <div className='flex items-center '>
          {item.available?(
            <Button
            onClick={handleOpen}
            sx={{
              width: "8%",
              height: "5vh",
              borderRadius: "10px",
              py: "10px",
              px:5,
              lg: "2",
              xs: "1",
              fontSize: 13,
            }}
            variant="contained"
            type="submit"
          >
           Borrow
          </Button>
          ):(<h2 className='text-red-500 pr-8' >not available</h2>)}

        
        </div>
        <Modal
        open={open}
        onClose={handleClose}
        aria-labelledby="modal-modal-title"
        aria-describedby="modal-modal-description"
      >
        <Box sx={style}>
          <Typography id="modal-modal-title" variant="h6" component="h2">
           Do you want to borrow this book?
          </Typography>
          <div>

          <h2 className='text-lg mt-5 font-semibold text-blue-800'>{item?.title}</h2>
        <h2>{item?.isbn}</h2>
        <h3>{item?.author}</h3>
        <h3>{item?.publicationYear}</h3>
          </div>
          <div className='mt-8 flex space-x-3 justify-end'>
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
              backgroundColor: "rgba(255, 0, 0, 0.9)", // Red with transparency
              // border: "2px solid darkred", // Dark red border
              color: "white", // White text color for contrast
              "&:hover": {
                backgroundColor: "rgba(255, 0, 0, 0.8)", // Darker red on hover
              },
            }}
            variant="contained"
            type="submit"
          >
           Cansel
          </Button>


          <Button
            onClick={handleBorrow}
            sx={{
              width: "8%",
              height: "5vh",
              borderRadius: "10px",
              py: "10px",
              px:5,
              lg: "2",
              xs: "1",
              fontSize: 13,
            }}
            variant="contained"
            type="submit"
          >
           Borrow
          </Button>

          </div>
        </Box>
      </Modal>
       
    </div>
  )
}
