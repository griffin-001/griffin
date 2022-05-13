/**
 * Card to display dependencies, could theoretically be used to display projects or vulnerabilities as well
 * by Chuyang Chen
 */

import React, { FunctionComponent } from 'react';
import {Box} from "@mui/system";
import {Card, CardContent, Typography} from "@mui/material";

interface OwnProps {
  item: Dependency
}

type Props = OwnProps;

const DependencyDisplay: FunctionComponent<Props> = (props) => {

  return (
      <Box>
        <Card variant="outlined" sx={{height: '75%'}}>
          <CardContent>
            <Typography variant="h5" component="div">
              {props.item.name}
            </Typography>
            <Typography sx={{ mb: 1.5 }}>
              Version {props.item.version}
            </Typography>
            <Typography variant="body2">
              {props.item.description}
            </Typography>
          </CardContent>
        </Card>
      </Box>);
};

export default DependencyDisplay;
