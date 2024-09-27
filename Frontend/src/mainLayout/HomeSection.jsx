import React from "react";
import Dashboard from "../pages/Statistics";
import { Grid2, Hidden } from "@mui/material";
import { Navbar } from "../pages/Navbar";

export const HomeSection = () => {
  return (
    <div>
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
        <Grid2 className="h-[99vh] " size={{ xs: 12, sm: 12, md: 12, lg: 9.5 }}>
          <Dashboard />
        </Grid2>
      </Grid2>
    </div>
  );
};
