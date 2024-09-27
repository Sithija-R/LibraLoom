import React from 'react'
import Button from "@mui/material/Button";
export const BookCard = ({item}) => {
  console.log("book ",item)
  return (
    <div className='w-full h-32 px-3 py-1 flex justify-between  bg-white dark:bg-slate-800 shadow-lg rounded-lg border border-slate-200 dark:border-slate-700'>
        <div>

        <h2 className='text-lg font-semibold'>{item?.title}</h2>
        <h2>{item?.isbn}</h2>
        <h3>{item?.author}</h3>
        <h3>{item?.publicationYear}</h3>
        </div>
        <div className='flex items-center '>
          {item.available?(
            <Button
            sx={{
              width: "8%",
              height: "5vh",
              borderRadius: "10px",
              py: "10px",

              lg: "2",
              xs: "1",
              fontSize: 13,
            }}
            variant="contained"
            type="submit"
          >
           Borrow
          </Button>
          ):("")}

        
        </div>
       
    </div>
  )
}
