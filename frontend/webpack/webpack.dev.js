const webpack = require('webpack');
const webpackMerge = require('webpack-merge').merge;
const BrowserSyncPlugin = require('browser-sync-webpack-plugin');
const SimpleProgressWebpackPlugin = require('simple-progress-webpack-plugin');
const WebpackNotifierPlugin = require('webpack-notifier');
const path = require('path');
const sass = require('sass');

const utils = require('./utils.js');
const commonConfig = require('./webpack.common.js');

const ENV = 'development';

const localhost = true; // set this to false before merging to clivan-cicd

module.exports = async options =>
  webpackMerge(await commonConfig({ env: ENV }), {
    devtool: 'cheap-module-source-map', // https://reactjs.org/docs/cross-origin-errors.html
    mode: ENV,
    entry: ['./src/main/webapp/app/index'],
    output: {
      path: utils.root('target/classes/static/'),
      filename: '[name].[contenthash:8].js',
      chunkFilename: '[name].[chunkhash:8].chunk.js',
    },
    optimization: {
      moduleIds: 'named',
    },
    module: {
      rules: [
        {
          test: /\.(sa|sc|c)ss$/,
          use: [
            'style-loader',
            {
              loader: 'css-loader',
              options: { url: false },
            },
            {
              loader: 'postcss-loader',
            },
            {
              loader: 'sass-loader',
              options: { implementation: sass },
            },
          ],
        },
      ],
    },
    devServer: {
      hot: true,
      static: {
        directory: './target/classes/static/',
      },
      allowedHosts: 'all',
      port: 9060,
      proxy: [
        /*
        {
          context: ['/services', '/management', '/v3/api-docs', '/h2-console', '/auth'],
          target: `http${options.tls ? 's' : ''}://localhost:8080`,
          secure: false,
          changeOrigin: options.tls,
        },
        */
        {
          context: ['/api', '/giftandgain'],
          target: localhost ? 'http://localhost:8756' : 'api-gateway-url',
          secure: false,
          changeOrigin: !localhost,
        },
        // {
        //   context: ['/api/report'],
        //   target: localhost ? 'http://localhost:8002' : 'https://qh7hxkd331.execute-api.us-east-1.amazonaws.com/report',
        //   secure: false,
        //   changeOrigin: !localhost,
        // },
        // {
        //   context: ['/api'],
        //   target: localhost ? 'http://localhost:8003' : 'https://qh7hxkd331.execute-api.us-east-1.amazonaws.com/userservice',
        //   secure: false,
        //   changeOrigin: !localhost,
        // },
        // {
        //   context: ['/giftandgain'],
        //   target: localhost ? 'http://localhost:8001' : 'https://qh7hxkd331.execute-api.us-east-1.amazonaws.com/inventory',
        //   secure: false,
        //   changeOrigin: !localhost,
        // },
      ],
      https: options.tls,
      historyApiFallback: true,
    },
    stats: process.env.JHI_DISABLE_WEBPACK_LOGS ? 'none' : options.stats,
    plugins: [
      process.env.JHI_DISABLE_WEBPACK_LOGS
        ? null
        : new SimpleProgressWebpackPlugin({
            format: options.stats === 'minimal' ? 'compact' : 'expanded',
          }),
      new BrowserSyncPlugin(
        {
          https: options.tls,
          host: 'localhost',
          port: 9001,
          proxy: {
            target: `http${options.tls ? 's' : ''}://localhost:${options.watch ? '8080' : '9060'}`,
            ws: true,
            proxyOptions: {
              changeOrigin: false, //pass the Host header to the backend unchanged  https://github.com/Browsersync/browser-sync/issues/430
            },
          },
          socket: {
            clients: {
              heartbeatTimeout: 60000,
            },
          },
          /*
      ,ghostMode: { // uncomment this part to disable BrowserSync ghostMode; https://github.com/jhipster/generator-jhipster/issues/11116
        clicks: false,
        location: false,
        forms: false,
        scroll: false
      } */
        },
        {
          reload: false,
        }
      ),
      new WebpackNotifierPlugin({
        title: 'Gift & Gain',
        contentImage: path.join(__dirname, 'logo-charity.png'),
      }),
      new webpack.DefinePlugin({
        'process.env.INVENTORY_MICROSERVICE_URL': JSON.stringify(process.env.INVENTORY_MICROSERVICE_URL),
        'process.env.AUTH_MICROSERVICE_URL': JSON.stringify(process.env.AUTH_MICROSERVICE_URL),
      }),
    ].filter(Boolean),
  });
