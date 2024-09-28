import React from "react";
import { useDispatch, useSelector } from "react-redux";
import { logOut } from "../../Storage/Auth/Actions";
import { useNavigate } from "react-router-dom";

export const AdminNavbar = () => {
  const dispatch = useDispatch();
  const { auth } = useSelector((store) => store);
  const navigate = useNavigate(); // React Router's navigation hook

  const handleLogout = () => {
    dispatch(logOut());
  };

  const handleNavigate = (path) => {
    navigate(path); // Navigation function
  };

  return (
    <div className="mt-5 ml-5 rounded-xl p-5 h-[95vh] bg-gradient-to-br from-gray-800 to-black flex flex-col justify-between">
      {/* User Greeting */}
      <div className="text-center mb-8">
        <h2 className="text-white text-lg font-semibold">
          Hi,{auth.user?.name}
        </h2>
      </div>

      {/* Centered Navigation Buttons */}
      <div className="flex-grow flex flex-col justify-center space-y-4">
        {/* User Management */}
        <button
          onClick={() => handleNavigate("./AdminUserMangemnt")}
          className="w-full bg-gradient-to-r from-gray-900 to-black text-white font-semibold py-2 px-4 rounded-lg transition-all hover:from-gray-700 hover:to-gray-500"
        >
          User Management
        </button>

        {/* Book Management */}
        <button
          onClick={() => handleNavigate("./AdminBookManagemnt")}
          className="w-full bg-gradient-to-r from-gray-900 to-black text-white font-semibold py-2 px-4 rounded-lg transition-all hover:from-gray-700 hover:to-gray-500"
        >
          Book Management
        </button>

        {/* Transactions */}
        <button
          onClick={() => handleNavigate("./AdminTransaction")}
          className="w-full bg-gradient-to-r from-gray-900 to-black text-white font-semibold py-2 px-4 rounded-lg transition-all hover:from-gray-700 hover:to-gray-500"
        >
          Transactions
        </button>
      </div>

      {/* Back to Panel and Logout Buttons */}
      <div className="space-y-4">
        {/* Back to Admin Panel */}
        <button
          onClick={() => handleNavigate("./")}
          className="w-full bg-gradient-to-r from-gray-900 to-black text-white font-semibold py-2 px-4 rounded-lg transition-all hover:from-gray-700 hover:to-gray-500"
        >
          Back to Admin Panel
        </button>

        {/* Logout Button at the Bottom */}
        <button
          onClick={handleLogout}
          className="w-full bg-gradient-to-r from-red-800 to-black text-white font-semibold py-2 px-4 rounded-lg transition-all hover:from-gray-700 hover:to-gray-500"
        >
          Logout
        </button>
      </div>
    </div>
  );
};
