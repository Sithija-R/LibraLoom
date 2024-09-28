import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom"; // Import useNavigate from React Router
import { useMyContext } from "../context/ContextProvider";
import useBorrowingNormalizer from "../data/dataNormalizer/borrowing";

import Icon1 from "../assets/icons/icon-01.svg";
import Icon2 from "../assets/icons/icon-02.svg";
import Icon3 from "../assets/icons/icon-03.svg";

import WelcomeBanner from "../layout/dashborad/WelcomeBanner";
import Total from "../layout/dashborad/statistics/Total";
import { BookCard } from "../layout/dashborad/statistics/BookCard";
import { useDispatch, useSelector } from "react-redux";
import { getAllBooks, searchBook } from "../../Storage/Book/Action";
import SearchIcon from "@mui/icons-material/Search";

function AdminPanel() {
  const { data } = useMyContext();
  const dispatch = useDispatch();
  const navigate = useNavigate(); // Use the navigate hook

  const { book } = useSelector((store) => store);

  // New state for search bar
  const [keyword, setKeyWord] = useState(null);

  const handleSearch = () => {
    dispatch(searchBook(keyword));
  };


  // Fetch books on component mount
  useEffect(() => {
    dispatch(getAllBooks());
  }, [dispatch]);


  // Filtered books based on search term


  // Calculate book statistics
  const totalBookCount = book.books?.length || 0;
  const availableBooksCount =
    book.books?.filter((item) => item.available).length || 0;
  const handedOverBooksCount = totalBookCount - availableBooksCount;

  return (
    <main>
      <div className="px-4 h-[92vh] overflow-y-scroll   sm:px-6 lg:px-8 py-8 w-full max-w-9xl mx-auto">
        
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
         
          <div className="col-span-4">
            <div
              onClick={() => navigate("/Admin/UserMangemnt")}
              className="cursor-pointer hover:bg-blue-100 transition p-4 rounded-lg "
            >
              <Total
                Icon={Icon1}
                title1="User Management"
                total={""}
                cardTitle={""}
              />
            </div>
          </div>

          <div className="col-span-4">
            <div
              onClick={() => navigate("/Admin/BookManagemnt")}
              className="cursor-pointer hover:bg-blue-100 transition p-4 rounded-lg "
            >
              <Total Icon={Icon2} title1="Book Management" total={""} />
            </div>
          </div>

          <div className="col-span-4">
            <div
              onClick={() => navigate("/Admin/Transaction")}
              className="cursor-pointer hover:bg-blue-100 transition p-4 rounded-lg "
            >
              <Total Icon={Icon3} title1="Transactions" total={""} />
            </div>
          </div>
        </div>

        {/* Search Bar Implementation */}
     

        {/* Book Statistics Box */}
        <div className="mt-6 bg-white p-6 rounded-lg shadow-md">
          <h3 className="text-lg font-bold mb-4">Library Statistics</h3>
          <div className="grid grid-cols-3 gap-4">
            <div className="bg-gray-100 p-4 rounded-md shadow">
              <h4 className="text-sm font-medium">Total Books</h4>
              <p className="text-xl font-semibold">{totalBookCount}</p>
            </div>
            <div className="bg-gray-100 p-4 rounded-md shadow">
              <h4 className="text-sm font-medium">Available Books</h4>
              <p className="text-xl font-semibold">{availableBooksCount}</p>
            </div>
            <div className="bg-gray-100 p-4 rounded-md shadow">
              <h4 className="text-sm font-medium">Handed Over Books</h4>
              <p className="text-xl font-semibold">{handedOverBooksCount}</p>
            </div>
          </div>
        </div>

        {/* Book summary */}
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
    </main>
  );
}

export default AdminPanel;
