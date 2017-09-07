import React, { Component } from 'react';
import {
  AppRegistry,
  StyleSheet,
  Text,
  View,
  TextInput,
  Switch,
  Dimensions,
  ScrollView
} from 'react-native';

import { AdTechView } from 'react-native-adtech';

const screenWidth = Dimensions.get('window').width
const screenHeight = 0.9*screenWidth

export default class example extends Component {

  render() {
    return (
      <ScrollView style={styles.container}>
        <Text style={styles.text}>
           Text Above Banner Add
        </Text>
        <AdTechView
          style={styles.empty_syle}
          alias={'home-top-5'}
          networkId={23}
          subnetworkId={4}
          type={'banner'}
          height={250}
          onAdFetchSuccess={() => console.log('New ad fetched')}
          />
        <Text style={styles.text}>
           Text Below Banner Add
        </Text>
        <AdTechView
          style={styles.empty_syle}
          alias={'interstitial-top-5'}
          networkId={23}
          subnetworkId={4}
          type={'interstitial'}
          height={0}/>
      </ScrollView>
    );
  }
}

const styles = StyleSheet.create({
  empty_style: {

  },
  container: {
    flex: 1,
  },
  /*container: {
    flex: 1,
        flexDirection: 'column',
        justifyContent: 'center',
        alignItems: 'center',
    backgroundColor: '#EEEEEE',
  },*/
  text: {
    textAlign: 'center',
    color: '#000000',
  },

});

AppRegistry.registerComponent('example', () => example);
