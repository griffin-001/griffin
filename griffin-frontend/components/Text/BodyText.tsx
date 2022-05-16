import React, {FunctionComponent, PropsWithChildren} from 'react';
import {Typography} from "@mui/material";

interface Props {
  bold?: boolean
}

const BodyText: FunctionComponent<PropsWithChildren<any>> = (props) => {

  return (
    <Typography
      variant={"body2"}
      sx={{
        fontWeight: props.bold ? "bold" : "none",
      }}
    >
      {props.children}
    </Typography>
  );
};

export default BodyText;