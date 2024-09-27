import React from "react";
import { useDispatch, useSelector } from "react-redux";
import { logOut } from "../../Storage/Auth/Actions";

export const Navbar = () => {

  const dispatch= useDispatch();
  const { auth } = useSelector((store) => store);

const handleLogout=()=>{

  dispatch(logOut());


}


  return (
    <div className="mt-5 ml-5 rounded-xl p-5 h-[95vh] bg-gradient-to-br from-slate-600 to-black">
      <div className="h-[80vh]">
        <h2 className="text-white">Hi,{auth.user?.name}</h2>
      </div>
      <div className="h-[14vh]">
        <h2 onClick={handleLogout} className="text-white">Logout</h2>
      </div>
    </div>
  );
};
