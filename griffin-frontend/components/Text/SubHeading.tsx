import React, {FunctionComponent, PropsWithChildren} from 'react';
import {Typography} from "@mui/material";

interface Props {

}

const SubHeading: FunctionComponent<PropsWithChildren<any>> = (props) => {

  return (
    <Typography variant={"h6"}>
      {props.children}
    </Typography>
  );
};

export default SubHeading;