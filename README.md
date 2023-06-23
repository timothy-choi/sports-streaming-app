# video-streaming-app


This app is a web app that will allow users to upload videos to groups called communities. In communities, users can rate videos, which in turn will automatically sort videos in that community in ascending order. If a video recieves a very low rating, it is placed in a group of videos that are given a "Time to Live" time limit in days. The videos will be removed from the community if their ratings do not improve past the requirement rating before that time limit expires to 0. The user will be able to set expiration date and the rating requirement when creating a community. Other than that, the user can view other user's uploaded videos, including ones that were banned from communities. 

Right now, the project is in its early stages. I'm currently working on the backend, developing API endpoints to set up User Authentication and Video upload and management. Specifically, I'm developing a system for processing videos that uses AWS SQS and SNS to queue videos for processing, as well as AWS Elastic Transcoder to process the videos so that they can playable. 
I'll update this when I make more progress. 