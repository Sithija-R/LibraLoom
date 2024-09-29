import React, { useEffect, useState } from "react";
import ArrowBackIcon from "@mui/icons-material/ArrowBack";
import { useNavigate } from "react-router-dom";
import { useDispatch, useSelector } from "react-redux";
import {
  deleteUser,
  findAllUsers,
  findUserById,
  findUserByName,
} from "../../Storage/Auth/Actions";
import Swal from "sweetalert2";

const UserManage = () => {
  const { auth, book } = useSelector((store) => store);

  const navigate = useNavigate();
  const [searchTerm, setSearchTerm] = useState("");
  const dispatch = useDispatch();
  const [searchBy, setSearchBy] = useState("name"); // Default search by name


  useEffect(() => {
    dispatch(findAllUsers());
    handleSearch();
  }, [auth.deleteUser,auth]);

  const fetchUsersByName = () => {
    dispatch(findUserByName(searchTerm));
  };

  const handleDelete = (userId) => {
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
        dispatch(deleteUser(userId));
        Swal.fire({
          title: "Deleted!",
          text: "Book has been deleted.",
          icon: "success",
        });
      }
    });
    
   
   
  };

  const fetchUsersById = () => {
    console.log(`Fetching users by ID: `, searchTerm);
    dispatch(findUserById(searchTerm));
  };

  const handleSearch = () => {
    if (searchBy === "name") {
      fetchUsersByName(searchTerm);
    } else {
      fetchUsersById(searchTerm);
    }
  };

  return (
    <div className="p-4 mt-1">
      <div
        onClick={() => navigate(-1)}
        className="flex w-20 cursor-pointer space-x-2 items-center"
      >
        <ArrowBackIcon />
        <h2 className="font-semibold text-lg">Back</h2>
      </div>

      <h2 className="text-2xl font-bold mb-4 text-blue-700">User Management</h2>

      {/* Search Criteria Selection */}
      <div className="mb-4 flex pr-5 items-center justify-end">
        <label className="mr-4">
          <input
            type="radio"
            value="name"
            checked={searchBy === "name"}
            onChange={(e) => setSearchBy(e.target.value)}
            className="mr-2"
          />
          Search by Name
        </label>
        <label>
          <input
            type="radio"
            value="id"
            checked={searchBy === "id"}
            onChange={(e) => setSearchBy(e.target.value)}
            className="mr-2"
          />
          Search by ID
        </label>
      </div>

      {/* Search Bar */}
      <input
        type="text"
        value={searchTerm}
        onChange={(e) => {
          setSearchTerm(e.target.value);
          handleSearch();
        }}
        placeholder={`Search users by ${searchBy}...`}
        className="w-full p-2 border border-gray-300 rounded-md mb-4"
      />
      <button
        className="bg-blue-500 text-white px-4 py-2 rounded-md mb-4"
        onClick={handleSearch}
      >
        Search
      </button>

      {/* User List */}
      <div className="h-[65vh] overflow-y-scroll border border-gray-300 rounded-md p-2">
        {searchTerm ? (
          auth.findUser ? (
            auth.findUser.map((item) => (
              <div className="flex justify-between items-center p-2 border-b">
                <div>
                  <h2>
                    <span className="font-semibold text-blue-700">Name : </span>
                    {item.name}
                  </h2>
                  <h2>
                    <span className="font-semibold text-blue-700">
                      Email :{" "}
                    </span>
                    {item.email}
                  </h2>
                  <h2>
                    <span className="font-semibold text-blue-700">Role : </span>
                    {item.role}
                  </h2>
                  {item.incompleteTransaction ? (
                    <div>
                      <h2>
                        <span className="font-semibold text-blue-700">
                          Incompleted Transactions{" "}
                        </span>
                      </h2>
                      <h3>
                        <span className="font-semibold ">
                          Transactions Id :{" "}
                        </span>
                        {item.incompleteTransaction.transactionId}
                      </h3>
                      <h3>
                        <span className="font-semibold ">Book : </span>
                        {item.incompleteTransaction.book.title}
                      </h3>
                      <h3>
                        <span className="font-semibold ">Due to : </span>
                        {item.incompleteTransaction.dueDate}
                      </h3>
                      {item.incompleteTransaction.lateDates > 0 ? (
                        <h3>
                          <span className="font-semibold text-red-500">
                            Late days:{" "}
                          </span>
                          {item.incompleteTransaction.lateDates}
                        </h3>
                      ) : null}
                    </div>
                  ) : (
                    ""
                  )}
                </div>

                <button
                  className="bg-red-500 text-white px-2 py-1 rounded-md"
                  onClick={() => handleDelete(item.userId)}
                >
                  Delete
                </button>
              </div>
            ))
          ) : (
            <h2>No users found!</h2>
          )
        ) : (
          auth.allUsers?.map((item) => (
            <div className="flex justify-between items-center p-2 border-b-2 border-b-slate-300">
            <div>
              <h2>
                <span className="font-semibold text-blue-700">Name : </span>
                {item.name}
              </h2>
              <h2>
                <span className="font-semibold text-blue-700">
                  Email :{" "}
                </span>
                {item.email}
              </h2>
              <h2>
                <span className="font-semibold text-blue-700">Role : </span>
                {item.role}
              </h2>
              {item.incompleteTransaction ? (
                <div>
                  <h2>
                    <span className="font-semibold text-blue-700">
                      Incompleted Transactions{" "}
                    </span>
                  </h2>
                  <h3>
                    <span className="font-semibold ">
                      Transactions Id :{" "}
                    </span>
                    {item.incompleteTransaction.transactionId}
                  </h3>
                  <h3>
                    <span className="font-semibold ">Book : </span>
                    {item.incompleteTransaction.book.title}
                  </h3>
                  <h3>
                    <span className="font-semibold ">Due to : </span>
                    {item.incompleteTransaction.dueDate}
                  </h3>
                  {item.incompleteTransaction.lateDates > 0 ? (
                    <h3>
                      <span className="font-semibold text-red-500">
                        Late days:{" "}
                      </span>
                      {item.incompleteTransaction.lateDates}
                    </h3>
                  ) : null}
                </div>
              ) : (
                ""
              )}
            </div>

            <button
              className="bg-red-500 text-white px-2 py-1 rounded-md"
              onClick={() => handleDelete(item.userId)}
            >
              Delete
            </button>
          </div>
          ))
        )}
      </div>
    </div>
  );
};

export default UserManage;
