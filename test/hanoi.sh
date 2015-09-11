#!/bin/bash

DIR=`dirname "$0"`

CP=${DIR}/../jill/target/jill-0.2.5-jar-with-dependencies.jar:${DIR}/../examples/target/jill-examples-0.2.5.jar:

CMD="java -cp ${CP} io.github.agentsoz.jill.Main --help"
echo $CMD; eval $CMD

DISCS=15
OUTFILE=${DIR}/hanoi.out
echo ""
echo "Solving Towers of Hanoi with ${DISCS} discs (see ${OUTFILE})"
CMD="java -cp ${CP} io.github.agentsoz.jill.Main --agent-class io.github.agentsoz.jill.example.hanoi.Player --logfile ${DIR}/hanoi.log --debug-level INFO --program-args \"-discs ${DISCS}\" --num-agents 1 --outfile ${OUTFILE}" 
echo "Started at " `date`
echo $CMD; eval $CMD > /dev/null
echo "Finished at" `date`

