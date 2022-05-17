import React, {FunctionComponent, PropsWithChildren} from 'react';
import {Typography} from "@mui/material";

interface Props {

}

const Heading: FunctionComponent<PropsWithChildren<any>> = (props) => {

  return (
    <Typography variant={"h5"}>
      {props.children}
    </Typography>
  );
};

export default Heading;