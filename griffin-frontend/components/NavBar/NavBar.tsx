/**
 * By Chuyang Chen
 *
 * The NavBar component allows user to go to home, report, profile, dashboard, or setup pages
 */

import {IconButton, AppBar, Typography, Toolbar, Box} from '@mui/material';
import React, {FunctionComponent} from 'react';
import NavBarBtn from "./NavBarBtn";
import {COLOURS} from "../../constants/colours";

interface Props {

}

const NavBar: FunctionComponent<Props> = (props) => {
  return (
    <Box sx={{
      position: "absolute",
      top: 0,
      left: 0,
      display: "flex",
      justifyContent: "center",
      alignItems: "center",
      height: "64px",
      width: "100%",
      paddingLeft: "1rem",
      borderBottom: "solid 1px " + COLOURS.BLACK_TEXT,
    }}>
      <Box sx={{
        display: "flex",
        flexDirection: "row",
        justifyContent: "center",
        alignItems: "center",
        width: "100%"
      }}>
        <Box component="img" src="/assets/griffin-logo.png" sx={{
          height: 50,
          width: 60,
          marginRight: "0.5rem",
        }}/>
        <Typography variant="h4" component={"div"} style={{flexGrow: 1}}
                    fontFamily={"Courier"} fontWeight={"bold"}>
          Griffin
        </Typography>
      </Box>
      <Box sx={{
        display: "flex",
        flexDirection: "row",
        paddingRight: "1rem"
      }}>
        <NavBarBtn text={"Home"} link={"/home"}/>
        <NavBarBtn text={"Report"} link={"/report"}/>
        <NavBarBtn text={"Dashboard"} link={"/dashboard"}/>
        <NavBarBtn text={"Setup"} link={"/setup"}/>
        <NavBarBtn text={"Profile"} link={"/profile"}/>
      </Box>
    </Box>
  )
}

export default NavBar;