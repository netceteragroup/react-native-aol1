/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 * @flow
 */

import React, { Component } from 'react';
import {
  AppRegistry,
  StyleSheet,
  Text,
  View
} from 'react-native';

import { AdTechView } from 'react-native-adtech';

export default class example extends Component {
  render() {
    const kValues = {
      kvenv: 'test',
      kvarticle: ['none']
    }

    return (
      <View style={styles.container}>
        <Text style={styles.welcome}>
          Welcome to AdTech React Native Example!
        </Text>
        <Text style={styles.instructions}>
          To get started, edit index.ios.js
        </Text>
        <Text style={styles.instructions}>
          Press Cmd+R to reload,{'\n'}
          Cmd+D or shake for dev menu
        </Text>
        <AdTechView
        	style={styles.adtech}
        	alias={"home-top-5"}
        	type={"banner"}
        	networkId={23}
        	subnetworkId={4}
          keyValues={kValues}
          onAdFetchSuccess={() => console.log('New ad fetched')}
        />
        <AdTechView
          alias={'interstitial-top-5'}
          networkId={23}
          subnetworkId={4}
          type={'interstitial'}
          height={0}/>
      </View>
    );
  }
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: '#F5FCFF',
  },
  welcome: {
    fontSize: 20,
    textAlign: 'center',
    margin: 10,
  },
  instructions: {
    textAlign: 'center',
    color: '#333333',
    marginBottom: 5,
  },
  adtech: {
    flex: 0,
    width: 380,
    height: 160,
  },
});

AppRegistry.registerComponent('example', () => example);
