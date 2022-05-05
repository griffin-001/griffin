import React, { FunctionComponent } from 'react';
import {Button, IconButton, Typography} from "@mui/material";
import Link from "next/link";

interface Props {
  text: string;
  link: string;
}

const NavBarBtn: FunctionComponent<Props> = (props) => {

  return (
    <Link href={props.link} passHref>
      <button style={{
        all: "unset",
        cursor: "pointer",
        padding: "0.5rem",
        marginLeft: "1rem",
      }}>
        <Typography variant="h6">
          {props.text}
        </Typography>
      </button>
    </Link>
  )
};

export default NavBarBtn;