import '../styles/globals.css'
import type { AppProps } from 'next/app'
import React from "react";
import Head from "next/head";

function MyApp({ Component, pageProps }: AppProps) {
  return (
    <React.Fragment>
      <Head>
        {/* eslint-disable-next-line @next/next/no-title-in-document-head */}
        <title>Griffin</title>
      </Head>

      {/* eslint-disable-next-line @next/next/no-page-custom-font */}
      <link
        rel="stylesheet"
        href="https://fonts.googleapis.com/css?family=Roboto:300,400,500,700&display=swap"
      />
      {/* eslint-disable-next-line @next/next/no-page-custom-font */}
      <link
        rel="stylesheet"
        href="https://fonts.googleapis.com/icon?family=Material+Icons"
      />
      <Component {...pageProps} />

    </React.Fragment>
  )
}

export default MyApp
