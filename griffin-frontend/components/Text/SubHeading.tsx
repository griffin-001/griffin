import React, {FunctionComponent, PropsWithChildren} from 'react';
import {Typography} from "@mui/material";

interface Props {
  noWrap?: boolean;
}

const SubHeading: FunctionComponent<PropsWithChildren<any>> = (props) => {

  return (
    <Typography variant={"h6"} noWrap={props.noWrap}>
      {props.children}
    </Typography>
  );
};

export default SubHeading;