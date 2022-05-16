import React, {FunctionComponent} from 'react';
import {Button, IconButton, Typography} from "@mui/material";
import Link from "next/link";
import SubHeading from "../Text/SubHeading";
import Heading from "../Text/Heading";

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
        <SubHeading noWrap>
          {props.text}
        </SubHeading>
      </button>
    </Link>
  )
};

export default NavBarBtn;