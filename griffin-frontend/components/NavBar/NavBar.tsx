/**
 * By Chuyang Chen
 *
 * The NavBar component allows user to go to home, report, profile, dashboard, or setup pages
 */

import {IconButton, AppBar, Typography, Toolbar, Box} from '@mui/material';
import React, { FunctionComponent } from 'react';
import NavBarBtn from "./NavBarBtn";
import {COLOURS} from "../../constants/colours";

interface Props {
 
}

const NavBar: FunctionComponent<Props> = (props) => {
    return (
      <Box sx={{
        display: "flex",
        justifyContent: "center",
        alignItems: "center",
        height: "64px",
        width: "100%",
        borderBottom: "solid 1px " + COLOURS.BLACK_TEXT,
      }}>
        <Box sx={{
          display: "flex",
          flexDirection: "row",
          width: "100%"
        }}>
          <Box component="img" src="/assets/griffin-logo.png" sx={{
            height: 35,
            width: 35,
          }}/>
          <Typography variant="h4" component={"div"} style={{flexGrow: 1}} fontFamily={"Courier"} fontWeight={"bold"}>
            Griffin
          </Typography>
        </Box>
        <Box sx={{
          display: "flex",
          flexDirection: "row",
          paddingRight: "1rem"
        }}>
          <NavBarBtn text={"Home"} link={"/"}/>
          <NavBarBtn text={"Report"} link={"/"}/>
          <NavBarBtn text={"Dashboard"} link={"/"}/>
          <NavBarBtn text={"Setup"} link={"/"}/>
          <NavBarBtn text={"Profile"} link={"/"}/>
        </Box>
      </Box>
    )
}

export default NavBar;