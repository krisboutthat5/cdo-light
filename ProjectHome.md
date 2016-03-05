## What is different in cdo-light compared to the original CDO? ##
  * there is no client/server architecture and therefore no marshaling through Net4j
  * there is no auditing and branching functionality
  * the java type of cdo ids is long (and not instances of CDOID)
  * there are no temporary IDs
  * there are no different id types (such as in the original CDO: CDOID.ObjectType)
  * there are no id providers
  * there are no id mapper
  * there are no time stamps
  * there is no replication support
  * there is no OSGi support

## What is the benefit of cdo-light and why are there so many features missing? ##
  * The main reason for this is to bring CDO to very resource constrained devices such as mobile devices like Android phones, tablets or Raspberry Pi.