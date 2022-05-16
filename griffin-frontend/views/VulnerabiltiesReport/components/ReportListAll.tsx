import React, {FunctionComponent} from "react";
import {Box} from "@mui/system";
import {
  Table, TableBody, TableCell,
  TableContainer, TableHead, TableRow,
  Typography
} from "@mui/material";
import Section from "../../../components/Section";
import Heading from "../../../components/Text/Heading";

interface Props {
  items: Array<Vulnerability>,
}

const ReportListAll: FunctionComponent<Props> = (props) => {
  if (props.items.length != 0) {
    return (
      <Section border marginBottom>
        <TableContainer sx={{maxHeight: 370}}>
          <Table stickyHeader aria-label="sticky table">
            <TableHead>
              <TableRow>
                <TableCell> Name </TableCell>
                <TableCell> Dependency </TableCell>
                <TableCell> Version </TableCell>
                <TableCell> Description </TableCell>
              </TableRow>
            </TableHead>
            <TableBody>
              {props.items.map((vul) => (
                <TableRow key={vul.name}>
                  <TableCell align="left">{vul.name}</TableCell>
                  <TableCell align="left">{vul.dependency.name}</TableCell>
                  <TableCell align="left">{vul.dependency.version}</TableCell>
                  <TableCell align="left">{vul.description}</TableCell>
                </TableRow>
              ))}
            </TableBody>
          </Table>
        </TableContainer>
      </Section>
    )
  } else {
    return (
      <Box sx={{mt: 2, mx: 5}}>
        <Heading>No Vulnerability!</Heading>
      </Box>
    )
  }
}

export default ReportListAll;