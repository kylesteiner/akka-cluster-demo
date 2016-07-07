## Build docker image
<pre>
`gradle distDocker`
</pre>
## Instantiate cluster
<pre>
`docker run --name seed1 -i -t -e "CLUSTER_PORT=3000" -p 3000:3000 akka-cluster-demo:1.0 --seed'`

`docker run --name seed2 -i -t -e "CLUSTER_PORT=3001" -p 3001:3001 akka-cluster-demo:1.0 --seed <seed1-ip>:3000`

`docker run --name worker1 -i -t akka-cluster-demo:1.0 <seed1-ip>:3000`

`docker run --name worker2 -i -t akka-cluster-demo:1.0 <seed2-ip>:3001`
</pre>
