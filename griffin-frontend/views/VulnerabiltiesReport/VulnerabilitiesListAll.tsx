import React, {FunctionComponent} from "react";
import {Box} from "@mui/system";
import {
    Table, TableBody, TableCell,
    TableContainer, TableHead, TableRow,
    Typography
} from "@mui/material";

interface Props {
    items: Array< Vulnerability>,
}

const VulnerabilitiesListAll: FunctionComponent<Props> = (props) => {
    if(props.items.length != 0){
        return(

            <Box sx={{mt:3, mx:5, border: 1}}>
                <TableContainer sx={{ maxHeight: 370 }}>
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
            </Box>
        )
    }else{
        return(
            <Box sx ={{mt:2, mx:5}}>
                <Typography variant = {"h4"}>No Vulnerability!</Typography>
            </Box>
        )
    }
}

export default VulnerabilitiesListAll;