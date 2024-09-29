import React from "react";
import Dashboard from "../pages/Statistics";
import { Grid2, Hidden } from "@mui/material";
import { Navbar } from "../pages/Navbar";
import { useSelector } from "react-redux";
import { AdminNavbar } from "../AdminPanel/AdminNavbar";
import { Route, Routes } from "react-router-dom";
import UserManage from "../AdminPanel/AdminUserMangemnt";
import BookManage from "../AdminPanel/AdminBookManagemnt";
import TransactionManage from "../AdminPanel/AdminTransaction";
import AdminPanel from "../AdminPanel/AdminPanle";

export const HomeSection = () => {
  const { auth } = useSelector((store) => store);
  console.log(auth);

  return (
    <div>





{/* 

<Grid2
          container
          className="w-full flex justify-between bg-slate-0 bg-slate-100 "
          spacing={9}
        >
          <Grid2 size={{ xs: 0, sm: 0, md: 0, lg: 2.5 }}>
            <Hidden lgDown>
             <AdminNavbar/>
            </Hidden>
          </Grid2>
          <Grid2
            className="h-[99vh] "
            size={{ xs: 12, sm: 12, md: 12, lg: 9.5 }}
          >
           <Routes>
           <Route path="/*" element={<AdminPanel />} />  
           <Route path="/Admin" element={<AdminPanel />} />  
           <Route path="/Admin/UserMangemnt" element={<UserManage />} />  
          <Route path="/Admin/BookManagemnt" element={<BookManage />} /> 
          <Route path="/Admin/Transaction" element={<TransactionManage />} />  
           </Routes>
          </Grid2>
        </Grid2> */}

























      {auth.user.role === "ADMIN" ? (
        <Grid2
          container
          className="w-full flex justify-between bg-slate-0 bg-slate-100 "
          spacing={9}
        >
          <Grid2 size={{ xs: 0, sm: 0, md: 0, lg: 2.5 }}>
            <Hidden lgDown>
             <AdminNavbar/>
            </Hidden>
          </Grid2>
          <Grid2
            className="h-[99vh] "
            size={{ xs: 12, sm: 12, md: 12, lg: 9.5 }}
          >
           <Routes>
           <Route path="/*" element={<AdminPanel />} />  
           <Route path="/Admin" element={<AdminPanel />} />  
           <Route path="/Admin/UserMangemnt" element={<UserManage />} />  
          <Route path="/Admin/BookManagemnt" element={<BookManage />} /> 
          <Route path="/Admin/Transaction" element={<TransactionManage />} />  
           </Routes>
          </Grid2>
        </Grid2>
      ) : (
        <Grid2
          container
          className="w-full flex justify-between bg-slate-0 bg-slate-100 "
          spacing={9}
        >
          <Grid2 size={{ xs: 0, sm: 0, md: 0, lg: 2.5 }}>
            <Hidden lgDown>
              <Navbar />
            </Hidden>
          </Grid2>
          <Grid2
            className="h-[99vh] "
            size={{ xs: 12, sm: 12, md: 12, lg: 9.5 }}
          >
            <Dashboard />
          </Grid2>
        </Grid2>
      )}
    </div>
  );
};
