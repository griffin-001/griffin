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
      <Button>
        <Typography variant="h6">
          {props.text}
        </Typography>
      </Button>
    </Link>
  )
};

export default NavBarBtn;