import React, {FunctionComponent, PropsWithChildren} from 'react';
import {Typography} from "@mui/material";

interface Props {
  noWrap?: boolean;
  center?: boolean;
}

const SubHeading: FunctionComponent<PropsWithChildren<any>> = (props) => {

  return (
    <Typography variant={"h6"} noWrap={props.noWrap}
                textAlign={props.center ? "center" : "left"}>
      {props.children}
    </Typography>
  );
};

export default SubHeading;