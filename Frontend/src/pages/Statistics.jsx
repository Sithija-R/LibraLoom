import { useMyContext } from "../context/ContextProvider";
import useBorrowingNormalizer from "../data/dataNormalizer/borrowing";

import Icon1 from "../assets/icons/icon-01.svg";
import Icon2 from "../assets/icons/icon-02.svg";
import Icon3 from "../assets/icons/icon-03.svg";

import WelcomeBanner from "../layout/dashborad/WelcomeBanner";
import Total from "../layout/dashborad/statistics/Total";
import { BookCard } from "../layout/dashborad/statistics/BookCard";
import { useDispatch, useSelector } from "react-redux";
import { useEffect, useState } from "react";
import { getAllBooks, returnBook, searchBook } from "../../Storage/Book/Action";
import { getUserProfile } from "../../Storage/Auth/Actions";
import { Box, Button, Modal, Typography } from "@mui/material";
import Swal from "sweetalert2";
import SearchIcon from "@mui/icons-material/Search";
function Dashboard() {
  const { data } = useMyContext();
  const dispatch = useDispatch();
  const jwt = localStorage.getItem("jwt");
  const { auth, book } = useSelector((store) => store);

  const [keyword, setKeyWord] = useState(null);

  const handleSearch = () => {
    dispatch(searchBook(keyword));
  };

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

  useEffect(() => {
    dispatch(getAllBooks());
    dispatch(getUserProfile(jwt));
    dispatch(searchBook(keyword));
  }, [book.borrowBook, book.returnBook]);

  const handleReturn = () => {
    const data = {
      transactionId: auth.user?.incompleteTransaction?.transactionId,
      userId: auth.user?.incompleteTransaction?.userId,
      isbn: auth.user?.incompleteTransaction?.book.isbn,
    };
    dispatch(returnBook(data));
    handleClose();
    Swal.fire({
      title: "Success!",
      text: "successfully returned.",
      icon: "success",
    });
  };

  return (
    <main>
      <div className="px-4 h-[92vh]  sm:px-6 lg:px-8 pt-5 w-full max-w-9xl mx-auto">
        <div className="mb-2 flex items-center justify-end ">
          <input
            type="text"
            className="w-80 rounded-3xl"
            onChange={(event) => {
              setKeyWord(event.target.value); // Set the keyword state
              handleSearch(); // Trigger search on every change
            }}
          />

          <SearchIcon
            onClick={handleSearch}
            className="text-slate-400 cursor-pointer hover:text-blue-600"
            sx={{ fontSize: 50 }}
          />
        </div>
        <WelcomeBanner />

        <div className="grid grid-cols-12 gap-6">
          <div
            className={`flex flex-col col-span-full sm:col-span-6 xl:col-span-4 bg-white dark:bg-slate-800 shadow-lg rounded-lg border border-slate-200 dark:border-slate-700`}
          >
            <div className="py-2 px-5">
              <header className="flex justify-between items-start mb-2">
                <img src={Icon1} width="20" height="20" alt="Icon 02" />
                <h2 className="font-semibold">Borrowed Book</h2>
              </header>
              {auth.user?.incompleteTransaction ? (
                <div className="flex justify-between">
                  <div className="items-start">
                    <h2 className="text-lg font-semibold">
                      {auth.user?.incompleteTransaction?.book.title}
                    </h2>
                    <p className="text-3d ">
                      by {auth.user?.incompleteTransaction?.book.author}
                    </p>
                    <p>
                      <span className="text-blue-600 font-semibold">
                        Transaction :{" "}
                      </span>
                      {auth.user?.incompleteTransaction?.uniqueId}
                    </p>
                    <h2 className=" text-slate-800 dark:text-slate-100 mb-2">
                      <span className="text-blue-600 font-semibold">
                        Due to :{" "}
                      </span>{" "}
                      {auth.user?.incompleteTransaction?.dueDate}
                    </h2>
                  </div>
                  <div className="flex justify-end items-center">
                    <Button
                      onClick={handleOpen}
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
                    >
                      return
                    </Button>
                  </div>
                </div>
              ) : (
                <h2>No borrowed book</h2>
              )}
            </div>
          </div>
          <Total Icon={Icon2} title1="ihihrb" total="56" />
          <Total Icon={Icon3} title1="Authors Featured" total="55" />

          {/* Display total of x */}
          {/* <Total Icon={Icon1} title1={auth.user?.borroweBooks.title} total={totalUsers} cardTitle={"Borrowed Books"}/> */}
        </div>
        {keyword? (
          <div>
            <h2 className="mt-2 font-semibold text-lg">Search Results..</h2>
            <div className="h-[40vh] overflow-y-scroll mt-4 space-y-2">
              {book.bookSearchResult?(
                book.bookSearchResult.map((item) => (
                  <BookCard item={item} />
                ))
              ):("No books available!")}
            </div>
          </div>
        ) : (
          <div>
            <h2 className="mt-2 font-semibold text-lg">All Books</h2>
            <div className="h-[40vh] overflow-y-scroll mt-4 space-y-2">
              {book.books?.map((item) => (
                <BookCard item={item} />
              ))}
            </div>
          </div>
        )}
      </div>

      <Modal
        open={open}
        onClose={handleClose}
        aria-labelledby="modal-modal-title"
        aria-describedby="modal-modal-description"
      >
        <Box sx={style}>
          <Typography id="modal-modal-title" variant="h6" component="h2">
            Do you want to return this book?
          </Typography>
          <div>
            <h2 className="text-lg mt-5 font-semibold text-blue-800">
              {" "}
              {auth.user?.incompleteTransaction?.book.title}
            </h2>
            <h2>{auth.user?.incompleteTransaction?.book.author}</h2>
          </div>
          <div className="mt-8 flex space-x-3 justify-end">
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
              type="submit"
            >
              Cancel
            </Button>

            <Button
              onClick={handleReturn}
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
              Return
            </Button>
          </div>
        </Box>
      </Modal>
    </main>
  );
}

export default Dashboard;
