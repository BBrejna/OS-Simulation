# Operating System Simulation Project Readme

## Overview
Welcome to the Operating System Simulation project made for an Operating Systems course @ Wrocław University of Science and Technology! 

This collaborative effort involves two team members working together to create a simulation of a real operating system. The simulation incorporates randomization in process generation and many combinations of algorithms and strategies.

## Table of Contents
- [Project Description](#project-description)
- [Team Members](#team-leader)
- [Features](#features)
- [Requirements](#requirements)
- [Screenshots](#screenshots)

## Project Description
The Operating System Simulation project aims to replicate actions of a real-world OS. It generates N random dataset of processes and tests each combination of prepared algorithms on all datasets and compares them.

## Team Leader
- Bartosz Brejna

## Team Member
- Aleksander Janic

## Features
1. **Process Generation:** Randomly generate processes and use locality factor for generating RAM requests.
2. **Test all combination of algorithms:** Run each possible combination on the same dataset.
3. **Rate combinations using prepared SCORE method:** Calculate score for each combination and sort them by this value.
4. **Many iterations for average results:** Run all 1536 combinations of algorithms on all prepared datasets and take an average value of SCORE.

## Requirements
- Java 21.0.1

## Screenshots
- Top 5 Combinations Of an Iteration
<img src="screenshots/resultsOfIterationTop5.png?raw=true">

- Top 30 Combinations Of a Simulation
<img src="screenshots/resultsOfSimulationTop30.png?raw=true">
