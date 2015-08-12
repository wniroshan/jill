#!/bin/bash

DIR=`dirname "$0"`

OUTDIR=${DIR}/tokenpassing-results
mkdir tokenpassing-results

CP=${DIR}/../jill/target/jill-0.0.1-SNAPSHOT-jar-with-dependencies.jar:${DIR}/../examples/target/jill-examples-0.0.1-SNAPSHOT.jar:

CMD="java -cp ${CP} agentsoz.jill.Main --help"
echo $CMD; $CMD

a=10000
r=100
v=2
ID=tokenpassing${v}-${a}a-${r}r
echo ""
echo "Running token passing version $v between $a agents for $r rounds (see ${ID}.*)"
CMD="java -Xmx2g -Xms2g -cp ${CP} agentsoz.jill.Main --agent-class agentsoz.jill.example.tokenpassing.TokenAgent$v --logfile ${OUTDIR}/${ID}.log --outfile ${OUTDIR}/${ID}.out --debug-level INFO --program-args \"-rounds $r\" --num-agents $a --threads 1"
echo "Started at " `date`
echo $CMD; eval $CMD > /dev/null
echo "Finished at" `date`

exit

v=1
ID=tokenpassing${v}-${a}a-${r}r
echo ""
echo "Running token passing version $v between $a agents for $r rounds (see ${ID}.*)"
CMD="java -Xmx2g -Xms2g -cp ${CP} agentsoz.jill.Main --agent-class agentsoz.jill.example.tokenpassing.TokenAgent$v --logfile ${OUTDIR}/${ID}.log --outfile ${OUTDIR}/${ID}.out --debug-level INFO --program-args \"-rounds $r\" --num-agents $a"
echo "Started at " `date`
echo $CMD; eval $CMD > /dev/null
echo "Finished at" `date`


a=100
r=5000
ID=tokenpassing-${a}a-${r}r
echo ""
echo "Running token passing between $a agents for $r rounds (see ${ID}.*)"
CMD="java -Xmx2g -Xms2g -cp ${CP} agentsoz.jill.Main --agent-class agentsoz.jill.example.tokenpassing.TokenAgent1 --logfile ${DIR}/${ID}.log --outfile ${DIR}/${ID}.out --debug-level INFO --program-args \"-rounds $r\" --num-agents $a"
echo "Started at " `date`
echo $CMD; eval $CMD > /dev/null
echo "Finished at" `date`


a=10000
r=1
ID=tokenpassing-${a}a-${r}r
echo ""
echo "Running token passing between $a agents for $r rounds (see ${ID}.*)"
CMD="java -Xmx2g -Xms2g -cp ${CP} agentsoz.jill.Main --agent-class agentsoz.jill.example.tokenpassing.TokenAgent1 --logfile ${DIR}/${ID}.log --outfile ${DIR}/${ID}.out --debug-level INFO --program-args \"-rounds $r\" --num-agents $a"
echo "Started at " `date`
echo $CMD; eval $CMD > /dev/null
echo "Finished at" `date`
