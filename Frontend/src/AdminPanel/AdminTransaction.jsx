import React, { useEffect, useState } from "react";
import TransactionCard from "./TransactionCard";
import { useDispatch, useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";
import { getTransaction, searchTransaction } from "../../Storage/Book/Action";

const TransactionManage = () => {
  const [searchTerm, setSearchTerm] = useState("");

  const dispatch = useDispatch();
  const navigate = useNavigate();

  const { auth, book } = useSelector((store) => store);

  const handleSearch = () => {
    console.log("Tran ",searchTerm)
    dispatch(searchTransaction(searchTerm));
  };

  useEffect(() => {
    dispatch(getTransaction());
    dispatch(searchTransaction(searchTerm));
  }, []);

  return (
    <div className=" mx-auto p-8">
      <h1 className="text-3xl font-semibold text-center text-gray-800 mb-6">
        Transaction
      </h1>

      <div className="flex justify-end">
        <div className="flex items-center justify-end mb-8 w-[50vw] ">
          <input
            type="text"
            value={searchTerm}
            onChange={(e) => {setSearchTerm(e.target.value)
            handleSearch()}}
            placeholder="Enter Transaction ID..."
            className="flex-grow p-3 border rounded-l-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
          />
          <button
            onClick={handleSearch}
            className="bg-blue-600 text-white p-3 rounded-r-lg hover:bg-blue-700 transition"
          >
            Search
          </button>
        </div>
      </div>
      <div className="flex flex-row w-full justify-between h-[73vh]">
        <div className="border-2 w-[48%] h-[73vh] overflow-y-scroll rounded-xl px-2 py-1">
          <h2 className="font-semibold text-lg">Pending Transactions</h2>

          {searchTerm ? (
            book.searchTransaction ? (
              book.searchTransaction
                .filter((item) => !item.completed)
                .map((item) => <TransactionCard item={item} />)
            ) : (
              <h2>No transactions found!</h2>
            )
          ) : book.transactions ? (
            book.transactions
              .filter((item) => !item.completed)
              .map((item) => <TransactionCard item={item} />)
          ) : (
            <h2>No transactions found!</h2>
          )}
        </div>
        <div className="border-2 w-[48%] h-[73vh] overflow-y-scroll rounded-xl px-2 py-1 ">
          <h2 className="font-semibold text-lg">Completed Transactions</h2>
          {searchTerm ? (
            book.searchTransaction ? (
              book.searchTransaction
                .filter((item) => item.completed)
                .map((item) => <TransactionCard item={item} />)
            ) : (
              <h2>No transactions found!</h2>
            )
          ) : book.transactions ? (
            book.transactions
              .filter((item) => item.completed)
              .map((item) => <TransactionCard item={item} />)
          ) : (
            <h2>No transactions found!</h2>
          )}
        </div>
      </div>
    </div>
  );
};

export default TransactionManage;
